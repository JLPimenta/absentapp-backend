package com.absentapp.project.domain.model.sala;

import com.absentapp.project.domain.core.mapper.IBaseMapper;
import com.absentapp.project.domain.entity.Sala;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISalaMapper extends IBaseMapper<Sala, SalaRequest, SalaResponse> {

    SalaBasicResponse toBasicReponse(Sala sala);

    Sala fromBasicRequest(SalaBasicRequest salaBasicRequest);

    List<SalaBasicResponse> toListBasicResponse(List<Sala> salas);
}
