ALTER TABLE service_offering
    ADD COLUMN IF NOT EXISTS status VARCHAR(20)
    NOT NULL DEFAULT 'ACTIVE';

CREATE INDEX IF NOT EXISTS idx_service_offering_company_id
    ON service_offering (company_id);

CREATE INDEX IF NOT EXISTS idx_service_offering_status
    ON service_offering (status);

CREATE INDEX IF NOT EXISTS idx_service_offering_name
    ON service_offering (name);