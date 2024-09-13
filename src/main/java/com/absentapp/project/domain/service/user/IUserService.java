package com.absentapp.project.domain.service.user;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.core.service.IBaseService;
import com.absentapp.project.domain.entity.User;
import com.absentapp.project.domain.model.User.ForgotPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService extends IBaseService<User> {
    User create(User user, MultipartFile file) throws DomainException, IOException;

    User update(User entity, MultipartFile file, String id) throws DomainException, IOException;

    byte[] getProfileImage(String fileName) throws IOException;

    User getContextUser();

    void sendResetPasswordEmail(HttpServletRequest request, ForgotPasswordRequest ForgotPassWordrequest);

    void changePassword(String token, String newPassword);
}
