DO
$$
BEGIN

    IF
EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'appointment'
          AND column_name = 'ent_at'
    )
    AND NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'appointment'
          AND column_name = 'end_at'
    ) THEN

ALTER TABLE appointment
    RENAME COLUMN ent_at TO end_at;

END IF;

END $$;


ALTER TABLE appointment
DROP
COLUMN IF EXISTS service_offering_id;


UPDATE appointment
SET status = 'SCHEDULED'
WHERE status IS NULL;


ALTER TABLE appointment
    ALTER COLUMN status SET DEFAULT 'SCHEDULED';


CREATE INDEX IF NOT EXISTS idx_appointment_company_id
    ON appointment(company_id);


CREATE INDEX IF NOT EXISTS idx_appointment_professional_id
    ON appointment(professional_id);


CREATE INDEX IF NOT EXISTS idx_appointment_client_id
    ON appointment(client_id);


CREATE INDEX IF NOT EXISTS idx_appointment_start_at
    ON appointment(start_at);


CREATE INDEX IF NOT EXISTS idx_appointment_status
    ON appointment(status);


CREATE INDEX IF NOT EXISTS idx_appointment_service_item_appointment
    ON appointment_service_item(appointment_id);