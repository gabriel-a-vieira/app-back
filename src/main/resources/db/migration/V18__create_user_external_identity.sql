CREATE TABLE user_external_identity
(
    id                 VARCHAR(38)  NOT NULL,

    user_id            VARCHAR(38)  NOT NULL,

    provider           VARCHAR(30)  NOT NULL,

    provider_user_id   VARCHAR(255) NOT NULL,

    provider_email     VARCHAR(255),

    created_at         TIMESTAMP    NOT NULL,
    updated_at         TIMESTAMP,

    created_by_user_id VARCHAR(38),
    updated_by_user_id VARCHAR(38),

    version            INTEGER,

    CONSTRAINT pk_user_external_identity
        PRIMARY KEY (id),

    CONSTRAINT fk_user_external_identity_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT uk_external_identity_provider_user
        UNIQUE (provider, provider_user_id),

    CONSTRAINT uk_external_identity_user_provider
        UNIQUE (user_id, provider)
);

CREATE INDEX idx_external_identity_user_id
    ON user_external_identity (user_id);

CREATE INDEX idx_external_identity_provider
    ON user_external_identity (provider);