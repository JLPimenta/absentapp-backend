package com.absentapp.project.domain.entity;

import com.absentapp.project.domain.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARTICIPANTE")
public class Participante extends BaseEntity {

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    @Column(name = "CODIGO_ACESSO", length = 30, nullable = false)
    private String codigoAcesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SALA", nullable = false, foreignKey = @ForeignKey(name = "SALA_FK"))
    private Sala sala;
}