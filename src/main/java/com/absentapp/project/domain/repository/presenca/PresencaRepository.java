package com.absentapp.project.domain.repository.presenca;

import com.absentapp.project.domain.core.repository.BaseRepository;
import com.absentapp.project.domain.entity.Presenca;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresencaRepository extends BaseRepository<Presenca> {
    Optional<Presenca> findByParticipanteIdAndSalaId(String participanteId, String salaId);

    void deleteAllBySalaId(String salaId);
}
