package com.absentapp.project.domain.model.User;

import jakarta.validation.constraints.NotBlank;

public record ChangeForgottenPasswordRequest(@NotBlank String password, @NotBlank String token) {
}
