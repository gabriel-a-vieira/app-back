ALTER TABLE country
    ADD COLUMN IF NOT EXISTS abbreviation VARCHAR(5);

CREATE UNIQUE INDEX IF NOT EXISTS uk_country_abbreviation
    ON country (abbreviation);