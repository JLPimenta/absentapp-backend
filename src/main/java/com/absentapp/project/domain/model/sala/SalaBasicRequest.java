package com.absentapp.project.domain.model.sala;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaBasicRequest {

    @Size(message = "O campo 'Nome da Sala' deve ter até 100 caracteres.", max = 100)
    @NotEmpty(message = "O campo 'Nome da Sala' não pode ser vazio.")
    String nome;

    @Size(message = "O campo 'Nome do Responsável' deve possuir de 2 a 100 caracteres.", min = 2, max = 100)
    @NotBlank(message = "O campo 'Nome do Responsável' não deve ser vazio.")
    String nomeResponsavel;

    @Size(message = "O campo 'Localidade' deve ter no máximo 200 caracteres.", max = 200)
    @NotBlank(message = "Localidade não pode ser vazio.")
    String localidade;

    @NotNull(message = "O campo Data Início da Vigência não pode ser vazio.")
    @FutureOrPresent(message = "Selecione uma data e hora igual ou maior a atual.")
    private LocalDate dataInicioVigencia;

    @NotNull(message = "O campo Data Fim da Vigência não pode ser vazio.")
    @FutureOrPresent(message = "Selecione uma data igual ou maior a atual.")
    private LocalDate dataFimVigencia;

    @Builder.Default
    private Boolean situacao = Boolean.TRUE;

}
