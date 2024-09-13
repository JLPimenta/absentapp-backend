package com.absentapp.project.domain.model.participante.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteCsvRep {

    @CsvBindByName(column = "nome participante", required = true)
    private String nome;

    @CsvBindByName(column = "codigo de acesso", required = true)
    private String codigoAcesso;
}
