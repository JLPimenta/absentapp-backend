package com.absentapp.project.domain.repository.participante;

import com.absentapp.project.domain.entity.Participante;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Builder
public class ParticipanteSpec implements Specification<Participante> {

    private final transient LocalDate dataInicio;
    private final transient LocalDate dataFim;

    @NotBlank(message = "O parâmetro id da sala no filtro não pode estar em branco.")
    private final transient String salaId;

    @Override
    public Predicate toPredicate(Root<Participante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Set<Predicate> predicates = new HashSet<>();

        Optional.ofNullable(dataInicio).filter(ObjectUtils::isNotEmpty).ifPresent(dataInicio -> {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicio.atStartOfDay()));
        });

        Optional.ofNullable(dataFim).filter(ObjectUtils::isNotEmpty).ifPresent(dataInicio -> {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), dataFim.atTime(23, 59, 59)));
        });

        Optional.ofNullable(salaId).ifPresent(id -> {
            predicates.add(criteriaBuilder.equal(root.get("sala").get("id"), id));
        });

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
