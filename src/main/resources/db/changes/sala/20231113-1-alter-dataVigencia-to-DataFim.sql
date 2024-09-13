ALTER TABLE sala
    DROP COLUMN DATA_FIM_VIGENCIA;

ALTER TABLE sala
    ADD data_inicio_vigencia date;

ALTER TABLE sala
    ADD data_fim_vigencia date;