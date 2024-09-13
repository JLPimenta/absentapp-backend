CREATE TABLE sala
(
    id                VARCHAR(36)  NOT NULL,
    data_criacao      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_atualizacao  TIMESTAMP WITHOUT TIME ZONE,
    nome              VARCHAR(50)  NOT NULL,
    nome_responsavel  VARCHAR(100) NOT NULL,
    localidade        VARCHAR(200) NOT NULL,
    data_fim_vigencia TIMESTAMP WITHOUT TIME ZONE,
    user_id           VARCHAR(36)  NOT NULL,
    CONSTRAINT pk_sala PRIMARY KEY (id)
);

ALTER TABLE sala
    ADD CONSTRAINT SALA_FK_01 FOREIGN KEY (user_id) REFERENCES usuario (id);