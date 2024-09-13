package com.absentapp.project.domain.model.sala;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaBasicResponse {
    private String id;
    private String nome;
    private String nomeResponsavel;
    private String localidade;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean situacao;
}
