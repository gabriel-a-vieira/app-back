ALTER TABLE country
    ADD COLUMN IF NOT EXISTS country_code VARCHAR(5);

ALTER TABLE country
    ADD COLUMN IF NOT EXISTS iso_n3_code VARCHAR(5);

ALTER TABLE country
    ADD COLUMN IF NOT EXISTS iso_a3_code VARCHAR(5);

ALTER TABLE country
    ADD COLUMN IF NOT EXISTS name_en VARCHAR(100);

ALTER TABLE country
    ADD COLUMN IF NOT EXISTS name_es VARCHAR(100);

CREATE UNIQUE INDEX IF NOT EXISTS uk_country_country_code
    ON country (country_code);

CREATE UNIQUE INDEX IF NOT EXISTS uk_country_iso_a3_code
    ON country (iso_a3_code);

CREATE INDEX IF NOT EXISTS idx_country_name
    ON country (name);