package com.absentapp.project.domain.model.participante;

import com.absentapp.project.domain.core.mapper.IBaseMapper;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.model.participante.csv.ParticipanteCsvRep;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IParticipanteMapper extends IBaseMapper<Participante, ParticipanteRequest, ParticipanteResponse> {
    Participante fromCsvRepresentation(ParticipanteCsvRep representation);
}
