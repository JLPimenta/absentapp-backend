CREATE TABLE usuario
(
    id               VARCHAR(36)  NOT NULL,
    data_criacao     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE,
    nome             VARCHAR(200) NOT NULL,
    email            VARCHAR(100) NOT NULL,
    password         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);