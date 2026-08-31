ALTER TABLE client
    ADD COLUMN IF NOT EXISTS user_id VARCHAR(38);

CREATE INDEX IF NOT EXISTS idx_client_user_id
    ON client(user_id);

CREATE UNIQUE INDEX IF NOT EXISTS uk_client_company_user
    ON client(company_id, user_id)
    WHERE user_id IS NOT NULL;