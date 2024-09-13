package com.absentapp.project.domain.service.user;

import com.absentapp.project.api.config.s3.StorageService;
import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.core.mail.IEmailService;
import com.absentapp.project.domain.core.service.BaseService;
import com.absentapp.project.domain.entity.ConfirmationToken;
import com.absentapp.project.domain.entity.User;
import com.absentapp.project.domain.model.User.ForgotPasswordRequest;
import com.absentapp.project.domain.repository.user.ConfirmationTokenRepository;
import com.absentapp.project.domain.repository.user.UserRepository;
import com.absentapp.project.domain.utils.Utility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

@Getter
@Service
public class UserService extends BaseService<User> implements IUserService {
    private final StorageService storageService;
    private final ConfirmationTokenRepository passwordTokenRepository;
    private final IEmailService emailService;

    public UserService(final StorageService storageService, final UserRepository repository, ConfirmationTokenRepository passwordTokenRepository, IEmailService emailService) {
        super(repository);
        this.storageService = storageService;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public User create(User user, MultipartFile file) throws DomainException, IOException {
        validate(user);

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setIsEnabled(true);

        User newUser = getRepository().save(user);
        newUser.setProfileImageName(uploadProfileImage(file));

        return newUser;
    }

    @Override
    public User update(User entity, MultipartFile file, String id) throws DomainException, IOException {
        User existent = getRepository().findById(id).orElseThrow(() -> new DomainException("Item não encontrado"));
        validate(entity, existent.getEmail());

        bind(existent, entity);
        existent.setProfileImageName(updateProfileImage(file));

        return getRepository().save(existent);
    }

    @Override
    public void bind(User entity, User update) {
        BeanUtils.copyProperties(update, entity, "id", "profileImageName", "password");
    }

    @Override
    public byte[] getProfileImage(String fileName) throws IOException {
        if (StringUtils.isBlank(fileName) || BooleanUtils.isFalse(storageService.fileExistByName(fileName)))
            return null;

        return getStorageService().downloadFile(fileName);
    }

    @Override
    public User getContextUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void validate(User user) throws DomainException {
        if (getRepository().findByEmail(user.getEmail()).isPresent())
            throw new DomainException("Um usuário com este e-mail já existe.");
    }

    private void validate(User user, String existingEmail) {
        boolean valida = getRepository().findByEmail(user.getEmail()).isPresent() && ObjectUtils.notEqual(user.getEmail(), existingEmail);

        if (BooleanUtils.isTrue(valida)) {
            throw new DomainException("Já existe uma conta com este e-mail cadastrado.");
        }
    }

    private String uploadProfileImage(MultipartFile file) throws IOException {
        if (Objects.isNull(file)) return null;

        return getStorageService().uploadFile(file);
    }

    private String updateProfileImage(MultipartFile file) throws IOException {
        String existentImg = getContextUser().getProfileImageName();

        if (Objects.isNull(existentImg)) return null;
        getStorageService().deleteFile(existentImg);

        return uploadProfileImage(file);
    }

    @Override
    public void sendResetPasswordEmail(HttpServletRequest request, ForgotPasswordRequest ForgotPassWordrequest) {
        User user = (User) getRepository().findByEmail(ForgotPassWordrequest.email()).orElseThrow(() -> new DomainException("Usuário não encontrado"));
        ConfirmationToken token = createResetPasswordToken(user);

        String link = Utility.getSiteURL(request) + "/reset-password?token=" + token.getToken();

        sendEmail(user.getEmail(), user.getName(), link);
    }

    private ConfirmationToken createResetPasswordToken(User user) {
        ConfirmationToken resetConfirmationToken = new ConfirmationToken();
        resetConfirmationToken.setUser(user);
        resetConfirmationToken.setActive(true);

        return getPasswordTokenRepository().save(resetConfirmationToken);
    }

    private void sendEmail(String email, String name, String link) {
        String subject = "Esqueci minha senha";

        String message = "<h3> Olá, " + name + "! </h3>" +
                "<h4>Você solicitou uma <strong>redefinição de senha</strong>.</h4>" +
                "<p>Clique no <strong>link abaixo</strong> e insira uma nova senha:</p>" +
                "<a href=\"" + link + "\" style=\"display: inline-block; padding: 10px; background-color: #00265F; color: #ffffff; text-decoration: none;\">Redefinir senha</a>" +
                "<p>Atenção: O token de alteração expira em <strong>10 minutos.</strong></p>" +
                "<p>Caso você não tenha solicitado recuperar sua senha, desconsidere esse email.</p>";

        getEmailService().send(email, subject, message);
    }

    @Override
    public void changePassword(String token, String newPassword) {
        ConfirmationToken confirmationToken = getPasswordTokenRepository().findByToken(token);

        isTokenExpiredOrInactive(confirmationToken);

        User user = confirmationToken.getUser();
        String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);

        confirmationToken.setActive(false);

        getPasswordTokenRepository().save(confirmationToken);
        getRepository().save(user);
    }

    private void isTokenExpiredOrInactive(ConfirmationToken confirmationToken) {
        var tokenExpiringDate = confirmationToken.getExpiryDate();
        Date today = Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")));

        if (BooleanUtils.isFalse(confirmationToken.getActive()) || tokenExpiringDate.before(today)) {
            throw new DomainException("Token expirado ou inativo. Tente novamente solicitando uma nova alteração de senha");
        }
    }

    @Override
    public UserRepository getRepository() {
        return (UserRepository) super.getRepository();
    }

    protected ConfirmationTokenRepository getPasswordTokenRepository() {
        return this.passwordTokenRepository;
    }
}
