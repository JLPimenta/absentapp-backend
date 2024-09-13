package com.absentapp.project.domain.model.sala;

import com.absentapp.project.domain.model.participante.ParticipanteResponse;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaResponse {
    private String id;
    private String nome;
    private String nomeResponsavel;
    private String localidade;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean situacao;
    private List<ParticipanteResponse> participantes;
}
