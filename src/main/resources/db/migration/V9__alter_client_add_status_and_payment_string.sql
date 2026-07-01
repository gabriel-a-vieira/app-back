ALTER TABLE client
    ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

DO $$
DECLARE
constraint_name_var TEXT;
BEGIN
SELECT con.conname
INTO constraint_name_var
FROM pg_constraint con
         JOIN pg_class rel ON rel.oid = con.conrelid
         JOIN pg_attribute att ON att.attrelid = rel.oid
WHERE rel.relname = 'client'
  AND att.attname = 'preferred_payment_method'
  AND con.contype = 'c'
  AND att.attnum = ANY(con.conkey)
    LIMIT 1;

IF constraint_name_var IS NOT NULL THEN
        EXECUTE 'ALTER TABLE client DROP CONSTRAINT ' || quote_ident(constraint_name_var);
END IF;
END $$;

ALTER TABLE client
    ALTER COLUMN preferred_payment_method DROP DEFAULT;

ALTER TABLE client
ALTER COLUMN preferred_payment_method TYPE VARCHAR(50)
USING preferred_payment_method::VARCHAR;

CREATE INDEX IF NOT EXISTS idx_client_company_id
    ON client (company_id);

CREATE INDEX IF NOT EXISTS idx_client_status
    ON client (status);

CREATE INDEX IF NOT EXISTS idx_client_person_id
    ON client (person_id);