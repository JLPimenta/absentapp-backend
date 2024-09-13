package com.absentapp.project.domain.core.mapper;

import com.absentapp.project.domain.core.entity.BaseEntity;

/*
 *
 * T ⇾ Entidade
 * R ⇾ Classe de requisição
 * Q ⇾ Classe de Resposta
 *
 * */
public interface IBaseMapper<T extends BaseEntity, R, Q> {
    Q toResponse(T entity);

    T fromRequest(R entityRequest);
}
