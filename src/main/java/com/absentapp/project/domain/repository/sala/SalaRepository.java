package com.absentapp.project.domain.repository.sala;

import com.absentapp.project.domain.core.repository.BaseRepository;
import com.absentapp.project.domain.entity.Sala;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends BaseRepository<Sala> {
    @Query("select (count(s) > 0) from Sala s where upper(s.nome) = upper(?1)")
    Boolean existsByNomeIgnoreCase(@NonNull String nome);
}
