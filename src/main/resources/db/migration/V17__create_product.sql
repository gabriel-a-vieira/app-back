CREATE TABLE product
(
    id                 VARCHAR(38)    NOT NULL,
    company_id         VARCHAR(38)    NOT NULL,

    name               VARCHAR(150)   NOT NULL,
    description        VARCHAR(2000),

    price              NUMERIC(12, 2) NOT NULL,
    stock_quantity     INTEGER        NOT NULL DEFAULT 0,

    image_url          VARCHAR(1000)  NOT NULL,

    status             VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE',

    created_at         TIMESTAMP      NOT NULL,
    updated_at         TIMESTAMP,

    created_by_user_id VARCHAR(38),
    updated_by_user_id VARCHAR(38),

    version            INTEGER,

    CONSTRAINT pk_product
        PRIMARY KEY (id),

    CONSTRAINT fk_product_company
        FOREIGN KEY (company_id)
            REFERENCES company (id),

    CONSTRAINT ck_product_price
        CHECK (price >= 0),

    CONSTRAINT ck_product_stock
        CHECK (stock_quantity >= 0)
);

CREATE INDEX idx_product_company_id
    ON product (company_id);

CREATE INDEX idx_product_status
    ON product (status);

CREATE INDEX idx_product_company_status
    ON product (company_id, status);

CREATE INDEX idx_product_name
    ON product (name);