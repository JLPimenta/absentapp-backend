package com.absentapp.project.domain.service.presenca;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.entity.Presenca;

public interface IPresencaService {
    Presenca registrarPresenca(String codigoAcesso, String salaId) throws DomainException;

    void deleteAllBySala(String salaId);
}
