package com.absentapp.project.domain.model.participante;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteResponse {
    private String id;
    private String nome;
    private String codigoAcesso;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}