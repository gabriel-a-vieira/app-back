ALTER TABLE country
    ADD COLUMN IF NOT EXISTS abbreviation VARCHAR(5);

UPDATE country
SET abbreviation = 'BR'
WHERE UPPER(name) IN ('BRASIL', 'BRAZIL')
  AND abbreviation IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uk_country_abbreviation
    ON country (abbreviation);

CREATE UNIQUE INDEX IF NOT EXISTS uk_state_abbreviation
    ON state (abbreviation);

INSERT INTO country (
    id,
    name,
    abbreviation,
    created_at,
    version
)
VALUES (
           '1063d5d0-c91e-4c9e-8b92-63015fe646ad',
           'Brasil',
           'BR',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (abbreviation) DO UPDATE
                                      SET
                                          name = EXCLUDED.name,
                                      updated_at = CURRENT_TIMESTAMP;

INSERT INTO state (
    id,
    name,
    abbreviation,
    created_at,
    version
)
VALUES
    ('6622a8bf-e0b9-4f94-adf6-88773aa72dda', 'Acre', 'AC', CURRENT_TIMESTAMP, 0),
    ('32c6900c-9ea1-40e6-9e0a-6b3e28ddc3b3', 'Alagoas', 'AL', CURRENT_TIMESTAMP, 0),
    ('3d79cae5-6ae0-42b7-abe5-b7211336df76', 'Amapá', 'AP', CURRENT_TIMESTAMP, 0),
    ('16e60f4e-859a-419a-a88c-239ac323e8f9', 'Amazonas', 'AM', CURRENT_TIMESTAMP, 0),
    ('3edb758b-bf5c-4fbb-a217-bc83cddee543', 'Bahia', 'BA', CURRENT_TIMESTAMP, 0),
    ('a16ccda7-815d-4bb7-8315-bba0e9c93368', 'Ceará', 'CE', CURRENT_TIMESTAMP, 0),
    ('c8b196fe-c0ae-42e1-91f0-f3ba1f1f1ad0', 'Distrito Federal', 'DF', CURRENT_TIMESTAMP, 0),
    ('624ebc04-bc3a-4f15-83a3-4f45baca846c', 'Espírito Santo', 'ES', CURRENT_TIMESTAMP, 0),
    ('d35eb4f7-84fe-4132-ac20-e13f7128b1ff', 'Goiás', 'GO', CURRENT_TIMESTAMP, 0),
    ('d97030f7-f346-4468-89c1-755daf9044b4', 'Maranhão', 'MA', CURRENT_TIMESTAMP, 0),
    ('8089e7c2-a23b-41fd-a9cf-585f0b90023e', 'Mato Grosso', 'MT', CURRENT_TIMESTAMP, 0),
    ('9590ead7-0fa5-4dbb-b3b9-7bd49e143f93', 'Mato Grosso do Sul', 'MS', CURRENT_TIMESTAMP, 0),
    ('d4e36002-0e99-49a8-a348-a0004440b699', 'Minas Gerais', 'MG', CURRENT_TIMESTAMP, 0),
    ('06f73410-822e-425f-967d-eaf99f5d9cef', 'Pará', 'PA', CURRENT_TIMESTAMP, 0),
    ('9fbc5c34-cce0-40df-8cc8-2cf19ea9fa3a', 'Paraíba', 'PB', CURRENT_TIMESTAMP, 0),
    ('5b5b0b08-27db-416a-8838-7126be2b6759', 'Paraná', 'PR', CURRENT_TIMESTAMP, 0),
    ('0f2019b0-7cb4-4616-a8cd-3fa1ce9c0102', 'Pernambuco', 'PE', CURRENT_TIMESTAMP, 0),
    ('bd4ded8e-19af-449e-becb-1e20f959c93e', 'Piauí', 'PI', CURRENT_TIMESTAMP, 0),
    ('4f333262-eda9-4561-8f31-4b7fd3e31564', 'Rio de Janeiro', 'RJ', CURRENT_TIMESTAMP, 0),
    ('60266510-38cd-4a3a-b6a4-6ac5252db7ba', 'Rio Grande do Norte', 'RN', CURRENT_TIMESTAMP, 0),
    ('d8b5d69a-82d0-4a0a-bcb8-d6aeabb94a1d', 'Rio Grande do Sul', 'RS', CURRENT_TIMESTAMP, 0),
    ('cf8639f7-e072-41ef-9e3e-0737219d8dc6', 'Rondônia', 'RO', CURRENT_TIMESTAMP, 0),
    ('12dbbe46-299b-4833-9cc7-9373107ba672', 'Roraima', 'RR', CURRENT_TIMESTAMP, 0),
    ('b5b9c286-5995-49c0-a273-2ec769bfdc13', 'Santa Catarina', 'SC', CURRENT_TIMESTAMP, 0),
    ('07bc84e4-fd4d-4c3e-bc3e-05caff4f50c3', 'São Paulo', 'SP', CURRENT_TIMESTAMP, 0),
    ('181cad23-b66f-4cc2-b217-6dc0153c3650', 'Sergipe', 'SE', CURRENT_TIMESTAMP, 0),
    ('04f37520-25f2-4bb1-a878-488f4e52091a', 'Tocantins', 'TO', CURRENT_TIMESTAMP, 0)
    ON CONFLICT (abbreviation) DO UPDATE
                                      SET
                                          name = EXCLUDED.name,
                                      updated_at = CURRENT_TIMESTAMP;