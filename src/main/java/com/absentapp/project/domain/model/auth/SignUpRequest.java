package com.absentapp.project.domain.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Size(message = "O campo Nome deve ter no máximo 200 caracteres", max = 200)
    private String name;

    @Email(message = "O campo deve ser um e-mail.")
    @NotBlank(message = "O campo E-mail não pode ser vazio.")
    @Size(message = "O campo E-mail deve ter no máximo 100 caracteres", max = 100)
    private String email;

    @NotBlank(message = "O campo senha não pode ser vazio.")
    @Size(message = "O campo Senha deve ter no máximo 200 caracteres", max = 50)
    private String password;

    private MultipartFile profileImage;
}
