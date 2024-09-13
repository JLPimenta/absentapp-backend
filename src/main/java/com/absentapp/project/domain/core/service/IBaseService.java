package com.absentapp.project.domain.core.service;

import com.absentapp.project.domain.core.entity.BaseEntity;
import com.absentapp.project.api.config.exception.DomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IBaseService<T extends BaseEntity> {
    T create(T entity) throws DomainException;

    T findById(String id) throws DomainException;

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    T update(T entity, String id) throws DomainException;

    void bind(T entity, T update);

    void delete(String id);

    void validate(T entity) throws DomainException;
}