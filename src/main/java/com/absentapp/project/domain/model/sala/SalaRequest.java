package com.absentapp.project.domain.model.sala;

import com.absentapp.project.domain.model.participante.ParticipanteRequest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaRequest {

    @Size(message = "O campo 'Nome da Sala' deve ter até 100 caracteres.", max = 100)
    @NotEmpty(message = "O campo 'Nome da Sala' não pode ser vazio.")
    private String nome;

    @Size(message = "O campo 'Nome do Responsável' deve possuir de 2 a 100 caracteres.", min = 2, max = 100)
    @NotBlank(message = "O campo 'Nome do Responsável' não deve ser vazio.")
    private String nomeResponsavel;

    @Size(message = "O campo 'Localidade' deve ter no máximo 200 caracteres.", max = 200)
    @NotBlank(message = "Localidade não pode ser vazio.")
    private String localidade;

    @Builder.Default
    private LocalDate dataInicioVigencia = LocalDate.now();

    @FutureOrPresent(message = "Selecione uma data e hora igual ou maior a atual.")
    private LocalDate dataFimVigencia;

    private Set<ParticipanteRequest> participantes = new HashSet<>();

    @Builder.Default
    private Boolean situacao = Boolean.TRUE;
}
