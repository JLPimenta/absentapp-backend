package com.absentapp.project.domain.service.presenca;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.entity.Presenca;
import com.absentapp.project.domain.entity.Sala;
import com.absentapp.project.domain.repository.participante.ParticipanteRepository;
import com.absentapp.project.domain.repository.presenca.PresencaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PresencaService implements IPresencaService {

    private final PresencaRepository repository;
    private final ParticipanteRepository participanteRepository;

    protected PresencaService(final PresencaRepository repository, final ParticipanteRepository participanteRepository) {
        this.repository = repository;
        this.participanteRepository = participanteRepository;
    }

    public Presenca registrarPresenca(String codigoAcesso, String salaId) throws DomainException {
        Participante participante = getParticipanteRepository().findByCodigoAcessoAndSalaId(codigoAcesso, salaId).orElseThrow(() -> new DomainException("Participante não encontrado"));
        Sala sala = participante.getSala();

        validate(participante, sala);

        return getRepository().save(presencaBuilder(participante, sala));
    }

    public void deleteAllBySala(String salaId) {
        getRepository().deleteAllBySalaId(salaId);
    }

    public void validate(Participante participante, Sala sala) {
        Presenca presenca = getRepository().findByParticipanteIdAndSalaId(participante.getId(), sala.getId()).orElse(null);

        if (Objects.nonNull(presenca) && Objects.equals(presenca.getDataRegistro().toLocalDate(), LocalDate.now())) {
            throw new DomainException("Já existe um registro de presença para a data de hoje.");
        }
    }

    private Presenca presencaBuilder(Participante participante, Sala sala) {
        return Presenca.builder()
                .participante(participante)
                .sala(sala)
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public PresencaRepository getRepository() {
        return repository;
    }

    public ParticipanteRepository getParticipanteRepository() {
        return participanteRepository;
    }
}
