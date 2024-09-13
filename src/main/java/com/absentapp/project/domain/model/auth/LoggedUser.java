package com.absentapp.project.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUser {
    private String id;
    private String name;
    private String email;
    private String stripeId;
    private String token;
}
