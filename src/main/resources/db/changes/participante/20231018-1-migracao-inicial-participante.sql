CREATE TABLE participante
(
    id               VARCHAR(36)                 NOT NULL,
    data_criacao     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE,
    nome             VARCHAR(100)                NOT NULL,
    codigo_acesso    VARCHAR(30)                 NOT NULL,
    id_sala          VARCHAR(36)                 NOT NULL,
    CONSTRAINT pk_participante PRIMARY KEY (id)
);

ALTER TABLE participante
    ADD CONSTRAINT SALA_FK FOREIGN KEY (id_sala) REFERENCES sala (id);