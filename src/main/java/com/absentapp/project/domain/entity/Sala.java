package com.absentapp.project.domain.entity;

import com.absentapp.project.domain.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SALA")
public class Sala extends BaseEntity {

    @Column(name = "NOME", length = 50, nullable = false)
    private String nome;

    @Column(name = "NOME_RESPONSAVEL", length = 100, nullable = false)
    private String nomeResponsavel;

    @Column(name = "LOCALIDADE", length = 200, nullable = false)
    private String localidade;

    @Column(name = "DATA_INICIO_VIGENCIA")
    private LocalDate dataInicioVigencia;

    @Column(name = "DATA_FIM_VIGENCIA")
    private LocalDate dataFimVigencia;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participante> participantes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "SALA_FK_01"), nullable = false)
    private User user;

    @Column(name = "SITUACAO", nullable = false)
    private Boolean situacao;
}