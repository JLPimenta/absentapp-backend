ALTER TABLE usuario
    ADD enabled BOOLEAN;

UPDATE usuario
SET enabled = TRUE
WHERE enabled IS NULL;