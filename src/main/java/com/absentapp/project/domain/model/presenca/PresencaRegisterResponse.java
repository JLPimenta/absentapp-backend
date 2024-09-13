package com.absentapp.project.domain.model.presenca;

import java.time.LocalDateTime;

public record PresencaRegisterResponse(String message, LocalDateTime dataRegistro, String nomeUsuario, String nomeSala) {
}
