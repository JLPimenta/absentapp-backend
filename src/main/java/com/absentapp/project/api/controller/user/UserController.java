package com.absentapp.project.api.controller.user;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.api.config.exception.ResponseError;
import com.absentapp.project.domain.core.model.SimpleMessageResponse;
import com.absentapp.project.domain.model.User.*;
import com.absentapp.project.domain.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "Usuário", description = "Controlador de usuário, específico para regras de negócio")
public class UserController {
    private final IUserService service;
    private final IUserMapper mapper;

    public UserController(IUserService service, IUserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "/image/", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Operation(description = "Retorna a imagem de perfil do usuário logado.")
    @ApiResponse(responseCode = "200", description = "Retorna a imagem do usuário logado. Não devolve nenhum conteúdo quando não houver imagem.")
    public ResponseEntity<byte[]> getUserProfileImage() throws IOException {
        String fileName = getService().getContextUser().getProfileImageName();

        byte[] imageBytes = getService().getProfileImage(fileName);

        return ResponseEntity.ok(imageBytes);
    }

    @PutMapping(consumes = {"multipart/form-data"})
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Atualiza um registro na base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro alterado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Registro não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public UserResponse update(@ModelAttribute @Valid UserRequest userRequest) throws DomainException, IOException {
        var userId = getService().getContextUser().getId();
        MultipartFile file = userRequest.getProfileImage();

        return getMapper()
                .toResponse(getService()
                        .update(getMapper()
                                .fromRequest(userRequest), file, userId));
    }

    @PostMapping("/password/send-reset-email")
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Envia um e-mail de confirmação de alteração de senha para o endereço informado.")
    public ResponseEntity<?> sendResetPasswordEmail(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request) {
        getService().sendResetPasswordEmail(request, forgotPasswordRequest);

        return ResponseEntity.ok(new SimpleMessageResponse("Enviamos um e‑mail para você. Siga as instruções para redefinir sua senha."));
    }

    @PatchMapping("/password/reset")
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Altera a senha do usuário mediante confirmação via e-mail.")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ChangeForgottenPasswordRequest request) {
        getService().changePassword(request.token(), request.password());

        return ResponseEntity.ok(new SimpleMessageResponse("Senha alterada com sucesso!"));
    }

    @GetMapping
    @Operation(description = "Retorna dados úteis do usuário logado.")
    public UserResponse getLoggedUser() {
        return getMapper().toResponse(getService().getContextUser());
    }
}
