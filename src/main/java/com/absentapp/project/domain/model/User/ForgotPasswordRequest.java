package com.absentapp.project.domain.model.User;

import jakarta.validation.constraints.Email;

public record ForgotPasswordRequest(@Email String email){}
