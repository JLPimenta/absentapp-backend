package com.absentapp.project.domain.service.participante;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.core.report.IJasperReportService;
import com.absentapp.project.domain.core.service.BaseService;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.entity.Sala;
import com.absentapp.project.domain.model.participante.filter.ParticipanteRequestFilter;
import com.absentapp.project.domain.repository.participante.ParticipanteRepository;
import com.absentapp.project.domain.repository.participante.ParticipanteSpec;
import com.absentapp.project.domain.repository.sala.SalaRepository;
import lombok.Getter;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Service
public class ParticipanteService extends BaseService<Participante> implements IParticipanteService {
    private final SalaRepository salaRepository;
    private final IJasperReportService<Participante> exporter;

    protected ParticipanteService(final ParticipanteRepository repository, final SalaRepository salaRepository, final IJasperReportService<Participante> exporter) {
        super(repository);
        this.salaRepository = salaRepository;
        this.exporter = exporter;
    }

    @Override
    public void validate(Set<Participante> participantes) throws DomainException {
        Set<String> codigosAcesso = new HashSet<>();

        for (Participante participante : participantes) {
            String codigoAcesso = participante.getCodigoAcesso();

            if (codigosAcesso.contains(codigoAcesso)) {
                throw new DomainException("Código de acesso duplicado: " + codigoAcesso);
            }

            codigosAcesso.add(codigoAcesso);
        }
    }

    @Override
    public Page<Participante> filter(ParticipanteRequestFilter participanteRequest, String salaId, Pageable pageable) {

        final ParticipanteSpec spec = ParticipanteSpec.builder()
                .dataInicio(participanteRequest.getDataInicio())
                .dataFim(participanteRequest.getDataFim())
                .salaId(salaId)
                .build();

        return getRepository().findAll(spec, pageable);
    }

    private void validate(Participante participante, String salaId) throws DomainException {
        String codigoAcesso = participante.getCodigoAcesso();

        if (getRepository().existsByCodigoAcessoAndSalaId(codigoAcesso, salaId) && ObjectUtils.notEqual(codigoAcesso, participante.getCodigoAcesso())) {
            throw new DomainException("Código de acesso duplicado: " + codigoAcesso);
        }
    }

    private void validate(Participante participante, Sala sala) throws DomainException {
        String codigoAcesso = participante.getCodigoAcesso();

        if (sala.getParticipantes().stream().anyMatch(p -> p.getCodigoAcesso().equals(codigoAcesso))) {
            throw new DomainException("Código de acesso duplicado: " + participante.getCodigoAcesso());
        }
    }

    @Override
    public Participante create(@NotNull Participante participante, @NotNull String salaId) throws DomainException {
        Sala sala = getSalaRepository().findById(salaId).orElseThrow(() -> new DomainException("Sala não encontrada"));
        validate(participante, sala);

        participante.setSala(sala);

        return super.create(participante);
    }

    @Override
    public List<Participante> create(@NotNull Set<Participante> participantes, @NotNull String salaId) throws DomainException {
        Sala sala = getSalaRepository().findById(salaId).orElseThrow(() -> new DomainException("Sala não encontrada"));

        List<Participante> participantesCriados = new ArrayList<>();

        for (Participante participante : participantes) {
            validate(participante, sala);
            participante.setSala(sala);

            participantesCriados.add(participante);
        }

        return getRepository().saveAll(participantesCriados);
    }

    @Override
    public Participante update(Participante participanteRequest, String id) throws DomainException {
        Participante participante = getRepository().findById(id).orElseThrow(() -> new DomainException("Item não encontrado"));
        Sala sala = participante.getSala();
        validate(participante, sala.getId());

        participanteRequest.setSala(sala);
        bind(participante, participanteRequest);

        return getRepository().save(participante);
    }

    @Override
    public byte[] export(String salaId, String reportFormat) throws IOException, JRException {
        final ParticipanteSpec spec = ParticipanteSpec.builder()
                .salaId(salaId)
                .build();

        List<Participante> participantes = getRepository().findAll(spec);
        var resourceLocation = "classpath:templates/relat_participantes.jrxml";

        return getExporter().generateReport(participantes, reportFormat, resourceLocation);
    }

    @Override
    public ParticipanteRepository getRepository() {
        return (ParticipanteRepository) super.getRepository();
    }
}
