package com.absentapp.project.domain.model.participante;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteRequest {

    @NotEmpty(message = "O campo 'Nome' não pode ser vazio.")
    @Size(min = 2, max = 100, message = "O nome deve ter no mínimo 2 e no máximo 100 caracteres.")
    private String nome;

    @NotEmpty(message = "O campo 'Código de Acesso' não pode ser vazio.")
    @Size(max = 30, message = "O campo 'Código de acesso' deve ter no máximo 30 caracteres.")
    private String codigoAcesso;
}
