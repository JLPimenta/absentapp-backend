package com.absentapp.project.domain.repository.participante;

import com.absentapp.project.domain.core.repository.BaseRepository;
import com.absentapp.project.domain.entity.Participante;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipanteRepository extends BaseRepository<Participante> {
    Boolean existsByCodigoAcessoAndSalaId(String codigoAcesso, String salaId);

    Optional<Participante> findByCodigoAcessoAndSalaId(String codigoAcesso, String SalaId);
}