CREATE TABLE presenca
(
    id               VARCHAR(36)                 NOT NULL,
    data_criacao     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE,
    sala_id          VARCHAR(36)                 NOT NULL,
    participante_id  VARCHAR(36)                 NOT NULL,
    data_registro    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_presenca PRIMARY KEY (id)
);

ALTER TABLE presenca
    ADD CONSTRAINT uc_398f21a6dbc601f63a73a14b4 UNIQUE (participante_id, sala_id, data_registro);

ALTER TABLE presenca
    ADD CONSTRAINT PRESENCA_FK_01 FOREIGN KEY (sala_id) REFERENCES sala (id);

ALTER TABLE presenca
    ADD CONSTRAINT PRESENCA_FK_018auPUu FOREIGN KEY (participante_id) REFERENCES participante (id);