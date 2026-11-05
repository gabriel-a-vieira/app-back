CREATE TABLE password_reset_code
(
    id                 VARCHAR(38)  NOT NULL,
    user_id            VARCHAR(38)  NOT NULL,
    code_hash          VARCHAR(100) NOT NULL,
    expires_at         TIMESTAMP    NOT NULL,
    attempts           INTEGER      NOT NULL DEFAULT 0,
    used_at            TIMESTAMP,

    created_at         TIMESTAMP    NOT NULL,
    updated_at         TIMESTAMP,
    created_by_user_id VARCHAR(38),
    updated_by_user_id VARCHAR(38),
    version            INTEGER,

    CONSTRAINT pk_password_reset_code
        PRIMARY KEY (id),

    CONSTRAINT fk_password_reset_code_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_password_reset_code_user_id
    ON password_reset_code (user_id);
