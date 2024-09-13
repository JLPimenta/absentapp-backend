package com.absentapp.project.domain.service.participante;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.core.service.IBaseService;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.model.participante.filter.ParticipanteRequestFilter;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IParticipanteService extends IBaseService<Participante> {

    Participante create(Participante participante, String salaId) throws DomainException;

    List<Participante> create(Set<Participante> participantes, String salaId) throws DomainException;

    Participante update(Participante participanteRequest, String id) throws DomainException;

    void validate(Set<Participante> participantes) throws DomainException;

    Page<Participante> filter(ParticipanteRequestFilter participanteRequest, String salaId, Pageable pageable);

    byte[] export(String salaId, String reportFormat) throws IOException, JRException;
}
