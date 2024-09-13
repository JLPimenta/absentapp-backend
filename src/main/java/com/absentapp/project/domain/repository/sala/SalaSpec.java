package com.absentapp.project.domain.repository.sala;

import com.absentapp.project.domain.entity.Sala;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Builder
public class SalaSpec implements Specification<Sala> {
    private final transient String userId;
    private final transient Boolean situacao;

    /**
     * @param root            must not be {@literal null}.
     * @param query           must not be {@literal null}.
     * @param criteriaBuilder must not be {@literal null}.
     * @return -> Filtro de salas
     */
    @Override
    public Predicate toPredicate(Root<Sala> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Set<Predicate> predicates = new HashSet<>();

        Optional.ofNullable(situacao).ifPresent(situacao -> {
            predicates.add(criteriaBuilder.equal(root.get("situacao"), situacao));
        });

        Optional.ofNullable(userId).ifPresent(id -> {
            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), id));
        });

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
