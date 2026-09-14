CREATE TABLE company_favorite
(
    id                 VARCHAR(38) NOT NULL,
    company_id         VARCHAR(38) NOT NULL,
    user_id            VARCHAR(38) NOT NULL,

    created_at         TIMESTAMP   NOT NULL,
    updated_at         TIMESTAMP,
    created_by_user_id VARCHAR(38),
    updated_by_user_id VARCHAR(38),
    version            INTEGER,

    CONSTRAINT pk_company_favorite
        PRIMARY KEY (id),

    CONSTRAINT uk_company_favorite_company_user
        UNIQUE (company_id, user_id)
);

CREATE INDEX idx_company_favorite_company_id
    ON company_favorite (company_id);

CREATE INDEX idx_company_favorite_user_id
    ON company_favorite (user_id);
