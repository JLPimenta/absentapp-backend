package com.absentapp.project.domain.job;

import com.absentapp.project.domain.entity.Sala;
import com.absentapp.project.domain.repository.sala.SalaRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
public class SalaFinalizerJob {
    private final SalaRepository repository;

    public SalaFinalizerJob(SalaRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void finalizarSalasPorVigencia() {
        List<Sala> salas = repository.findAll();

        LocalDate today = LocalDate.now();

        for (Sala sala : salas) {
            LocalDate dataFim = sala.getDataFimVigencia();

            Duration duration = Duration.between(today.atStartOfDay(), dataFim.atStartOfDay());

            if (duration.isNegative() && BooleanUtils.isTrue(sala.getSituacao())) {
                sala.setSituacao(false);
                repository.save(sala);
            }
        }
    }
}
