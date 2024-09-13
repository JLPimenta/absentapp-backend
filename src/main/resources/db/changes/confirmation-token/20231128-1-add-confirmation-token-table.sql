CREATE TABLE reset_token
(
    id               VARCHAR(36)  NOT NULL,
    data_criacao     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE,
    user_id          VARCHAR(36)  NOT NULL,
    token            VARCHAR(255) NOT NULL,
    expiry_date      TIMESTAMP WITHOUT TIME ZONE,
    active           BOOLEAN      NOT NULL,
    CONSTRAINT pk_reset_token PRIMARY KEY (id)
);

ALTER TABLE reset_token
    ADD CONSTRAINT uc_reset_token_token UNIQUE (token);

ALTER TABLE reset_token
    ADD CONSTRAINT PASS_TOKEN_FK_01 FOREIGN KEY (user_id) REFERENCES usuario (id);