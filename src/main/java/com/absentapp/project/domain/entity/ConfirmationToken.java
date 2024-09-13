package com.absentapp.project.domain.entity;

import com.absentapp.project.domain.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESET_TOKEN")
public class ConfirmationToken extends BaseEntity {

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "PASS_TOKEN_FK_01"), nullable = false, updatable = false)
    private User user;

    @Column(name = "TOKEN", nullable = false, unique = true, updatable = false)
    private String token;

    @Column(updatable = false)
    private Date expiryDate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @Override
    protected void prePersist() {
        this.expiryDate = Date.from(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")));
        this.token = UUID.randomUUID().toString();
        super.prePersist();
    }
}