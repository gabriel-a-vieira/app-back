DROP TABLE role_permission;

CREATE TABLE user_permission
(
    id                 VARCHAR(38) NOT NULL,
    company_id         VARCHAR(38),
    user_id            VARCHAR(38) NOT NULL,
    module             VARCHAR(30) NOT NULL,

    can_create         BOOLEAN     NOT NULL DEFAULT FALSE,
    can_update         BOOLEAN     NOT NULL DEFAULT FALSE,
    can_list           BOOLEAN     NOT NULL DEFAULT FALSE,
    can_delete         BOOLEAN     NOT NULL DEFAULT FALSE,

    created_at         TIMESTAMP   NOT NULL,
    updated_at         TIMESTAMP,
    created_by_user_id VARCHAR(38),
    updated_by_user_id VARCHAR(38),
    version            INTEGER,

    CONSTRAINT pk_user_permission
        PRIMARY KEY (id),

    CONSTRAINT uk_user_permission_user_module
        UNIQUE (user_id, module)
);

CREATE INDEX idx_user_permission_company_id
    ON user_permission (company_id);
