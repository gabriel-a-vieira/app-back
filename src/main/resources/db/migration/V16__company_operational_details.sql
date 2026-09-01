CREATE TABLE company_payment_method
(
    company_id     VARCHAR(38) NOT NULL,
    payment_method VARCHAR(30) NOT NULL,

    CONSTRAINT fk_company_payment_method_company
        FOREIGN KEY (company_id)
            REFERENCES company (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_company_payment_method
        UNIQUE (company_id, payment_method)
);


CREATE TABLE company_amenity
(
    company_id VARCHAR(38) NOT NULL,
    amenity    VARCHAR(50) NOT NULL,

    CONSTRAINT fk_company_amenity_company
        FOREIGN KEY (company_id)
            REFERENCES company (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_company_amenity
        UNIQUE (company_id, amenity)
);


CREATE TABLE company_opening_hour
(
    company_id VARCHAR(38) NOT NULL,
    day_week   VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time   TIME NOT NULL,

    CONSTRAINT fk_company_opening_hour_company
        FOREIGN KEY (company_id)
            REFERENCES company (id)
            ON DELETE CASCADE,

    CONSTRAINT ck_company_opening_hour_time
        CHECK (start_time < end_time),

    CONSTRAINT uk_company_opening_hour
        UNIQUE (
                company_id,
                day_week,
                start_time,
                end_time
            )
);


CREATE INDEX idx_company_opening_hour_company
    ON company_opening_hour (company_id);

CREATE INDEX idx_company_opening_hour_day
    ON company_opening_hour (company_id, day_week);