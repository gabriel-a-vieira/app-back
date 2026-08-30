CREATE TABLE company_review
(
    id         VARCHAR(38) NOT NULL,
    company_id VARCHAR(38) NOT NULL,
    user_id    VARCHAR(38) NOT NULL,

    rating     INTEGER     NOT NULL,
    comment    VARCHAR(2000),
    image_url  VARCHAR(1000),

    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    version    BIGINT,

    CONSTRAINT pk_company_review
        PRIMARY KEY (id),

    CONSTRAINT uk_company_review_company_user
        UNIQUE (company_id, user_id),

    CONSTRAINT ck_company_review_rating
        CHECK (rating BETWEEN 1 AND 5)
);

CREATE INDEX idx_company_review_company_id
    ON company_review (company_id);

CREATE INDEX idx_company_review_user_id
    ON company_review (user_id);

CREATE INDEX idx_company_review_rating
    ON company_review (rating);