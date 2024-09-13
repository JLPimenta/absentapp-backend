package com.absentapp.project.domain.model.participante.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteRequestFilter {
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
