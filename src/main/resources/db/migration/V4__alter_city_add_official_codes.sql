ALTER TABLE city
    ADD COLUMN IF NOT EXISTS tom_code VARCHAR(10);

ALTER TABLE city
    ADD COLUMN IF NOT EXISTS ibge_code VARCHAR(10);

ALTER TABLE city
    ADD COLUMN IF NOT EXISTS tom_name VARCHAR(100);

CREATE UNIQUE INDEX IF NOT EXISTS uk_city_ibge_code
    ON city (ibge_code);

CREATE INDEX IF NOT EXISTS idx_city_id_state
    ON city (id_state);

CREATE INDEX IF NOT EXISTS idx_city_id_country
    ON city (id_country);

CREATE INDEX IF NOT EXISTS idx_city_name_state
    ON city (name, id_state);