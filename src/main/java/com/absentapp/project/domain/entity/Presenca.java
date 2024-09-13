package com.absentapp.project.domain.entity;

import com.absentapp.project.domain.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PARTICIPANTE_ID", "SALA_ID", "DATA_REGISTRO"})
})
public class Presenca extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "SALA_ID", foreignKey = @ForeignKey(name = "PRESENCA_FK_01"), nullable = false)
    private Sala sala;
    @ManyToOne
    @JoinColumn(name = "PARTICIPANTE_ID", foreignKey = @ForeignKey(name = "PRESENCA_FK_01"), nullable = false)
    private Participante participante;

    @Column(name = "DATA_REGISTRO", nullable = false)
    private LocalDateTime dataRegistro;
}
