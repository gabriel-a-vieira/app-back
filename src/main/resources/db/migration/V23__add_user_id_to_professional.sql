ALTER TABLE professional
    ADD COLUMN IF NOT EXISTS user_id VARCHAR(38);

CREATE INDEX IF NOT EXISTS idx_professional_user_id
    ON professional(user_id);

CREATE UNIQUE INDEX IF NOT EXISTS uk_professional_company_user
    ON professional(company_id, user_id)
    WHERE user_id IS NOT NULL;
