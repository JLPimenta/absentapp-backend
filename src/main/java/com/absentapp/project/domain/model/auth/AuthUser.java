package com.absentapp.project.domain.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthUser(
        @Email(message = "O campo deve ser um e-mail.")
        @NotBlank(message = "O campo E-mail não pode estar em branco")
        String login,
        @NotBlank(message = "O campo Senha não pode estar em branco")
        String senha) {
}
