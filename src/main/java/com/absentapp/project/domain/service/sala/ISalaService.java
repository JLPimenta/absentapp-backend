package com.absentapp.project.domain.service.sala;

import com.absentapp.project.domain.core.service.IBaseService;
import com.absentapp.project.domain.entity.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISalaService extends IBaseService<Sala> {
    Page<Sala> findAll(Boolean situacao, Pageable pageable);

    List<Sala> findAll(Boolean situacao);
}
