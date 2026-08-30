ALTER TABLE company_review
    ADD COLUMN IF NOT EXISTS created_by_user_id VARCHAR(38);

ALTER TABLE company_review
    ADD COLUMN IF NOT EXISTS updated_by_user_id VARCHAR(38);


ALTER TABLE company_review
ALTER COLUMN version TYPE INTEGER
    USING version::INTEGER;


UPDATE company_review
SET created_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;


ALTER TABLE company_review
    ALTER COLUMN created_at SET NOT NULL;