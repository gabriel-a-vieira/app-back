CREATE UNIQUE INDEX IF NOT EXISTS uk_country_country_code ON country (country_code);

CREATE UNIQUE INDEX IF NOT EXISTS uk_country_iso_a3_code ON country (iso_a3_code);

CREATE INDEX IF NOT EXISTS idx_country_name ON country (name);

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Não Definido',
           'ZZZ',
           '000',
           '898',
           'ZZZ',
           'Not defined',
           'No definido',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '32a03d47-9a07-50a7-9bf6-8c2b7c56f17b',
           'Afeganistão',
           'AFG',
           '013',
           '004',
           'AFG',
           'Afghanistan',
           'Afganistan',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '883ce8f1-b470-52b9-996a-fe60ddecac0b',
           'Aland, Ilhas',
           'ALA',
           '015',
           '248',
           'ALA',
           'Aland Islands',
           'Alans, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0234a0f8-ca7c-5746-9bfc-b783e866a760',
           'Albânia',
           'ALB',
           '017',
           '008',
           'ALB',
           'Albania',
           'Albania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'afbf6b76-3dc4-59af-b01c-e15daf5a5dc7',
           'Alboran-Perejil, Ilhas',
           'ESP',
           '020',
           '724',
           'ESP',
           'Alboran-Perejil, Islands',
           'Alboran-Perejil, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '778235d4-628e-5036-9762-c8697bc13ba7',
           'Alemanha',
           'DEU',
           '023',
           '276',
           'DEU',
           'Germany',
           'Alemania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '778235d4-628e-5036-9762-c8697bc13ba7',
           'Alemanha Oriental',
           'DEU',
           '025',
           '278',
           'DEU',
           'East Germany',
           'Alemania del Este',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '70aef036-01d6-54dc-94b7-1ab9c6a0af98',
           'Burkina Faso',
           'BFA',
           '031',
           '854',
           'BFA',
           'Burkina Faso',
           'Burkina Faso',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd3f0088f-39db-52a3-847c-14ad7ae521c6',
           'Andorra',
           'AND',
           '037',
           '020',
           'AND',
           'Andorra',
           'Andorra',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '763f4d6c-b4db-5d37-bf32-6aae2d8d3de7',
           'Angola',
           'AGO',
           '040',
           '024',
           'AGO',
           'Angola',
           'Angola',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9f390c02-793c-51de-97ec-d22acef1d3f8',
           'Anguilla',
           'AIA',
           '041',
           '660',
           'AIA',
           'Anguilla',
           'Anguilla',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd2c2f4d0-57cc-5179-98ff-6c4912ae22ea',
           'Antártica',
           'ATA',
           '042',
           '010',
           'ATA',
           'Antarctica',
           'Antártida',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '8750f464-870b-51b3-81ce-96a79f213347',
           'Antígua e Barbuda',
           'ATG',
           '043',
           '028',
           'ATG',
           'Antigua and Barbuda',
           'Antigua y Barbuda',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f7f39594-9cfe-5224-9b39-c07434d31fd1',
           'Antilhas Holandesas',
           'ANT',
           '047',
           '530',
           'ANT',
           'Netherlands Antilles',
           'Antillas Holandesas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6a6b2763-e3f5-51e9-a1ca-029bbc6bf4fc',
           'Arábia Saudita',
           'SAU',
           '053',
           '682',
           'SAU',
           'Saudi Arabia',
           'Arabia Saudita',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f67bfcf6-36d5-5d90-940c-8d5ff53cdbf1',
           'Argélia',
           'DZA',
           '059',
           '012',
           'DZA',
           'Algeria',
           'Argelia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'ff83ee82-1d06-5321-adcd-1e166052e200',
           'Argentina',
           'ARG',
           '063',
           '032',
           'ARG',
           'Argentina',
           'Argentina',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e453c9eb-1486-5ff1-882f-2b1c6d83c76f',
           'Armênia',
           'ARM',
           '064',
           '051',
           'ARM',
           'Armenia',
           'Armenia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4ceea239-690b-5a79-9bd6-b0b766eff596',
           'Aruba',
           'ABW',
           '065',
           '533',
           'ABW',
           'Aruba',
           'Aruba',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b4aac3c8-f30f-5afa-852d-93fc48e06aca',
           'Austrália',
           'AUS',
           '069',
           '036',
           'AUS',
           'Australia',
           'Australia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1e3ef5b6-36cf-5ebf-9e60-90323ec8a7fa',
           'Áustria',
           'AUT',
           '072',
           '040',
           'AUT',
           'Austria',
           'Austria',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7efb8633-bf43-5220-8843-8b063044d1ac',
           'Azerbaijão',
           'AZE',
           '073',
           '031',
           'AZE',
           'Azerbaijan',
           'Acerbaiyan',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '00e64a3e-0645-5f52-b18c-c58789599dad',
           'Bahamas',
           'BHS',
           '077',
           '044',
           'BHS',
           'Bahamas',
           'Bahamas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2015419f-b19d-5dbb-8a00-d65f1e57605a',
           'Barein',
           'BHR',
           '080',
           '048',
           'BHR',
           'Bahrein',
           'Bahrein',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2c58fe3c-7a73-58f9-8549-155b323d015c',
           'Bangladesh',
           'BGD',
           '081',
           '050',
           'BGD',
           'Bangladesh',
           'Bangladesh',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0a1377c4-9156-5240-89c2-ba4c9bc15110',
           'Barbados',
           'BRB',
           '083',
           '052',
           'BRB',
           'Barbados',
           'Barbados',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd27df9bd-3bb3-5790-98c4-748f707c9eca',
           'Belarus',
           'BLR',
           '085',
           '112',
           'BLR',
           'Belarus',
           'Belarús',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '061bfa28-f9ce-5ecf-b1e5-1a12d3ac20af',
           'Bélgica',
           'BEL',
           '087',
           '056',
           'BEL',
           'Belgium',
           'Bélgica',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'eb6a760f-74e1-57b5-b6c8-4bcd788ce3b0',
           'Belize',
           'BLZ',
           '088',
           '084',
           'BLZ',
           'Belize',
           'Belice',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '43f39b9f-02a8-5ba6-a36d-d88d7a88b705',
           'Bermudas',
           'BMU',
           '090',
           '060',
           'BMU',
           'Bermuda',
           'Bermuda',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '982d8bf1-9230-58bd-a4b4-534a4340d966',
           'Mianmar',
           'MMR',
           '093',
           '104',
           'MMR',
           'Myanmar',
           'Myanmar',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd7cc718f-1b37-5fa6-bfd4-2ec7a7370234',
           'Bolívia',
           'BOL',
           '097',
           '068',
           'BOL',
           'Bolivia',
           'Bolivia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'fbd56cce-a308-5176-b3b2-6adf90c40a05',
           'Bósnia-Herzegovina',
           'BIH',
           '098',
           '070',
           'BIH',
           'Bosnia and Herzegovina',
           'Bosnia y Herzegovina',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '725417c9-0952-53cb-98db-99f753c8f0ee',
           'Bonaire, Saint Eustatius e Saba',
           'BES',
           '099',
           '535',
           'BES',
           'Bonaire, Saint Eustatius and Saba',
           'Bonaire, Saint Eustatius y Saba',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7c078470-6692-5bfb-8e4a-65fb0b4b7ae7',
           'Internação na Zona Franca de Manaus',
           'BRA',
           '100',
           '898',
           'BRA',
           'Internment in the Manaus Free Trade Zone',
           'Internamiento de la Zona Franca de Manaus',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b70651b2-4a25-59e6-95c3-be22985006e1',
           'Botsuana',
           'BWA',
           '101',
           '072',
           'BWA',
           'Botswana',
           'Botswana',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bab2205a-79f3-5f24-ba74-2e3157abd3cf',
           'Bouvet, Ilha',
           'BVT',
           '102',
           '074',
           'BVT',
           'Bouvet Island',
           'Bouvet, Isla',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7c078470-6692-5bfb-8e4a-65fb0b4b7ae7',
           'Brasil',
           'BRA',
           '105',
           '076',
           'BRA',
           'Brazil',
           'Brasil',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4be44ed1-dc7f-5749-8456-56573996c333',
           'Brunei',
           'BRN',
           '108',
           '096',
           'BRN',
           'Brunei',
           'Brunei',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '8c7a8ded-32c1-5f80-bc2b-90f19f348d68',
           'Bulgária',
           'BGR',
           '111',
           '100',
           'BGR',
           'Bulgaria',
           'Bulgaria',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '54124e6d-e486-5fea-8d4f-497375515cc6',
           'Burundi',
           'BDI',
           '115',
           '108',
           'BDI',
           'Burundi',
           'Burundi',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '87ef7929-5d64-5b34-a5b2-05e09ea588f9',
           'Butão',
           'BTN',
           '119',
           '064',
           'BTN',
           'Bhutan',
           'Bhután',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7cfa3452-67e4-59f5-a1bc-7c1da6dae98c',
           'Cabo Verde',
           'CPV',
           '127',
           '132',
           'CPV',
           'Cape Verde',
           'Cabo Verde',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5f66ef37-e88d-5ce5-ad01-2e310553d68f',
           'Cayman, Ilhas',
           'CYM',
           '137',
           '136',
           'CYM',
           'Cayman Islands',
           'Caimán, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'a777aca4-1cea-56e7-93c2-2fb3e05fb575',
           'Camboja',
           'KHM',
           '141',
           '116',
           'KHM',
           'Cambodia',
           'Camboya',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e0092c5d-0074-55fd-8e0d-f3aaa7c44f8a',
           'Camarões',
           'CMR',
           '145',
           '120',
           'CMR',
           'Cameroon',
           'Camerún',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '197febf0-4142-527f-b8c0-86f50ad05b10',
           'Canadá',
           'CAN',
           '149',
           '124',
           'CAN',
           'Canada',
           'Canadá',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '3fee063e-a600-52f1-9d5b-180ae7e3e423',
           'Canal, Ilhas do (Guernsey)',
           'GGY',
           '150',
           '831',
           'GGY',
           'Guernsey',
           'Guernsey',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'afbf6b76-3dc4-59af-b01c-e15daf5a5dc7',
           'Canárias, Ilhas',
           'ESP',
           '151',
           '724',
           'ESP',
           'Canary, Islands',
           'Canarias, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1e218f81-0d66-53a2-9c61-18982add48df',
           'Canal, Ilhas do (Jersey)',
           'GBR',
           '152',
           '826',
           'GBR',
           'Jersey',
           'Jersey',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'ee93f52d-1be6-5228-ba16-edeacad8dc00',
           'Cazaquistão',
           'KAZ',
           '153',
           '398',
           'KAZ',
           'Kazakhstan',
           'Kazakhstán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '040c64c9-a399-577c-984a-9fa845dc72cc',
           'Catar',
           'QAT',
           '154',
           '634',
           'QAT',
           'Qatar',
           'Qatar',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e56a81ed-1f3c-5cc4-9adc-15eb48bff88b',
           'Chile',
           'CHL',
           '158',
           '152',
           'CHL',
           'Chile',
           'Chile',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '39c54989-f29a-5a64-923c-9f6ffd660e81',
           'China',
           'CHN',
           '160',
           '156',
           'CHN',
           'China',
           'China',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0f29e0c5-80dc-527e-a0fa-ffc3cb57d114',
           'Taiwan (Formosa)',
           'TWN',
           '161',
           '158',
           'TWN',
           'Taiwan',
           'Taiwán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c5a41a2b-2cef-5c55-b118-347c1bbc2d4a',
           'Chipre',
           'CYP',
           '163',
           '196',
           'CYP',
           'Cyprus',
           'Chipre',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '64601350-fde1-55d1-a6f5-e4e37fc53eec',
           'Cocos (Keeling), Ilhas',
           'CCK',
           '165',
           '166',
           'CCK',
           'Cocos (Keeling) Islands',
           'Cocos (Keeling), Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'cb349cd7-ac33-5a0f-924e-84c6b7846407',
           'Colômbia',
           'COL',
           '169',
           '170',
           'COL',
           'Colombia',
           'Colombia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '16569c48-ff5f-52a2-8ddc-fde4317ecdda',
           'Comores',
           'COM',
           '173',
           '174',
           'COM',
           'Comoros',
           'Comoras',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2c05f393-b764-5ef8-bef8-6631efdf33f1',
           'Congo',
           'COG',
           '177',
           '178',
           'COG',
           'Congo',
           'Congo',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '95e6891e-8d96-5835-aac8-1540deb06530',
           'Cook, Ilhas',
           'COK',
           '183',
           '184',
           'COK',
           'Cook Islands',
           'Cook, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '755284bc-2bbc-5d6c-a425-53705bb92758',
           'Coreia do Norte',
           'PRK',
           '187',
           '408',
           'PRK',
           'North Korea',
           'Corea (Norte)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '559be9e2-b1ad-5332-a928-d71985fd9aae',
           'Coreia do Sul',
           'KOR',
           '190',
           '410',
           'KOR',
           'South Korea',
           'Corea (Sur)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '16084c2e-8a84-518b-9086-ef58d5684937',
           'Costa do Marfim',
           'CIV',
           '193',
           '384',
           'CIV',
           'Cote D''Ivore',
           'Costa de Marfil',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '41cf62b0-52b4-50d6-93a6-053c01ebb46c',
           'Croácia',
           'HRV',
           '195',
           '191',
           'HRV',
           'Croatia',
           'Croacia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '13310fbf-716d-5c38-a3b3-a0c37def61ad',
           'Costa Rica',
           'CRI',
           '196',
           '188',
           'CRI',
           'Costa Rica',
           'Costa Rica',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '30618eab-30c0-5f9a-85cf-bd48f6494c4a',
           'Coveite (Kuweit)',
           'KWT',
           '198',
           '414',
           'KWT',
           'Kuwait',
           'Kuwait',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2c5bdba6-d98e-5ccb-b378-4db075d10665',
           'Cuba',
           'CUB',
           '199',
           '192',
           'CUB',
           'Cuba',
           'Cuba',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'fb669d73-8302-5d89-82ca-fe718e4fe5c7',
           'Curaçao',
           'CUW',
           '200',
           '531',
           'CUW',
           'Curaçao',
           'Curazao',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6e6bfcb4-53ea-56e5-b5c4-6f96b1beb633',
           'Benin',
           'BEN',
           '229',
           '204',
           'BEN',
           'Benin',
           'Benin',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '41e5ed85-46d0-5a5a-9e12-2a8f4de55878',
           'Dinamarca',
           'DNK',
           '232',
           '208',
           'DNK',
           'Denmark',
           'Dinamarca',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7e530bc5-5184-5f12-9e3b-9dcb03e29fd9',
           'Dominica',
           'DMA',
           '235',
           '212',
           'DMA',
           'Dominica Island',
           'Dominica',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2bcc7fe3-4069-5dae-a616-f278834ccd59',
           'Dubai',
           'ARE',
           '237',
           '784',
           'ARE',
           'Dubai',
           'Dubai',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '30b1d1ac-85b3-572e-a608-4a0d4d77764f',
           'Equador',
           'ECU',
           '239',
           '218',
           'ECU',
           'Ecuador',
           'Ecuador',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7dad6907-054c-57bb-b975-9334aa1f1958',
           'Egito',
           'EGY',
           '240',
           '818',
           'EGY',
           'Egypt',
           'Egipto',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '3d2f1ec5-5d92-55f3-bc6e-b8c5a33d5ff7',
           'Eritreia',
           'ERI',
           '243',
           '232',
           'ERI',
           'Eritrea',
           'Eritrea',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2bcc7fe3-4069-5dae-a616-f278834ccd59',
           'Emirados Árabes Unidos',
           'ARE',
           '244',
           '784',
           'ARE',
           'United Arab Emirates',
           'Emiratos Árabes Unidos',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'afbf6b76-3dc4-59af-b01c-e15daf5a5dc7',
           'Espanha',
           'ESP',
           '245',
           '724',
           'ESP',
           'Spain',
           'España',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '97c33b98-e5b2-5827-90bf-8613290c64af',
           'Eslovênia',
           'SVN',
           '246',
           '705',
           'SVN',
           'Slovenia',
           'Eslovenia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '38406371-6c86-5f66-9401-9f10c7bf8ae8',
           'Eslováquia',
           'SVK',
           '247',
           '703',
           'SVK',
           'Slovakia',
           'Eslovaquia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '222fa0b6-f07b-5cd2-89f0-19dddae3cd10',
           'Estados Unidos',
           'USA',
           '249',
           '840',
           'USA',
           'United States',
           'Estados Unidos',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1c71e0c4-94df-5ffb-ba3d-3ff91985faf2',
           'Estônia',
           'EST',
           '251',
           '233',
           'EST',
           'Estonia',
           'Estonia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'cfaf0879-8113-5af7-bef9-52ad2e3c4d31',
           'Etiópia',
           'ETH',
           '253',
           '231',
           'ETH',
           'Ethiopia',
           'Etiopía',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '98e2744c-30e4-579a-a37f-b77919e64f03',
           'Falkland (Malvinas)',
           'FLK',
           '255',
           '238',
           'FLK',
           'Falkland Islands (Malvinas)',
           'Malvinas (Falkland), Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '68014b7c-02ab-58ba-82df-ae232d537c14',
           'Faroe, Ilhas',
           'FRO',
           '259',
           '234',
           'FRO',
           'Faroe Islands',
           'Feroé, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '14f84058-edd1-5988-9443-4ee62a04a179',
           'Filipinas',
           'PHL',
           '267',
           '608',
           'PHL',
           'Philippines',
           'Filipinas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5fb0004e-a7da-59d4-9d9e-c9a9a38d48bd',
           'Finlândia',
           'FIN',
           '271',
           '246',
           'FIN',
           'Finland',
           'Finlandia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'df5ce9d2-d9d3-5e26-8fdf-280a33dc8e6a',
           'França',
           'FRA',
           '275',
           '250',
           'FRA',
           'France',
           'Francia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4430df07-1a3d-5f45-8dca-6bb4f2f8b4cc',
           'Gabão',
           'GAB',
           '281',
           '266',
           'GAB',
           'Gabon',
           'Gabón',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd92ab1dc-a8ba-5705-beba-50532dd938b8',
           'Gâmbia',
           'GMB',
           '285',
           '270',
           'GMB',
           'Gambia',
           'Gambia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b76eb7ec-c4f6-5194-ac5f-ca9297b7449b',
           'Gana',
           'GHA',
           '289',
           '288',
           'GHA',
           'Ghana',
           'Ghana',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6e6d6d4f-24cc-5886-bb34-a6e04de4c0c1',
           'Geórgia',
           'GEO',
           '291',
           '268',
           'GEO',
           'Georgia',
           'Georgia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'db107655-31e3-59f7-a46e-e8cc3165ca5f',
           'Geórgia do Sul e Sandwich do Sul, Ilhas',
           'SGS',
           '292',
           '239',
           'SGS',
           'South Georgia and the South Sandwich Islands',
           'Islas Georgias del Sur y Sandwich del Sur',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1af19ee0-72f4-52f5-9ad3-7a12cbf30990',
           'Gibraltar',
           'GIB',
           '293',
           '292',
           'GIB',
           'Gibraltar',
           'Gibraltar',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'ebff3617-ee6f-59d5-ae70-218c72e94bc6',
           'Granada',
           'GRD',
           '297',
           '308',
           'GRD',
           'Grenada',
           'Granada',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bc8b7393-1731-5f73-bfa1-15f16c55b608',
           'Grécia',
           'GRC',
           '301',
           '300',
           'GRC',
           'Greece',
           'Grecia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c27d3971-bffb-5bd8-aee0-b52492e14b56',
           'Groenlândia',
           'GRL',
           '305',
           '304',
           'GRL',
           'Greenland',
           'Groenlandia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7797ee75-2a49-5e1e-9cc6-b4479f914299',
           'Guadalupe',
           'GLP',
           '309',
           '312',
           'GLP',
           'Guadeloupe',
           'Guadalupe',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '516d830b-536c-5e3e-b535-f1489e6a00f8',
           'Guam',
           'GUM',
           '313',
           '316',
           'GUM',
           'Guam',
           'Guam',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '520f98ac-e07d-5ba0-b0a2-50c3f4377721',
           'Guatemala',
           'GTM',
           '317',
           '320',
           'GTM',
           'Guatemala',
           'Guatemala',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '3fee063e-a600-52f1-9d5b-180ae7e3e423',
           'Guernsey',
           'GGY',
           '321',
           '898',
           'GGY',
           'Guernsey',
           'Guernsey',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '06a35e4c-6620-523f-9f05-c81be07cc11b',
           'Guiana Francesa',
           'GUF',
           '325',
           '254',
           'GUF',
           'French Guyana',
           'Guyana Francesa',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1018feae-5c61-5817-8303-f778981448be',
           'Guiné',
           'GIN',
           '329',
           '324',
           'GIN',
           'Guinea',
           'Guinea',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '863e97f6-6aa5-533d-9b1a-7b618fa51504',
           'Guiné Equatorial',
           'GNQ',
           '331',
           '226',
           'GNQ',
           'Equatorial Guinea',
           'Guinea Ecuatorial',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '04d08649-ba20-5c49-b90e-a231444d8fcd',
           'Guiné-Bissau',
           'GNB',
           '334',
           '624',
           'GNB',
           'Guinea-Bissau',
           'Guinea-Bissau',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '93ecb643-4a49-539f-b307-70e32a9804ad',
           'Guiana',
           'GUY',
           '337',
           '328',
           'GUY',
           'Guyana',
           'Guyana',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '601ad549-7268-5a47-abd4-28f7db72cdf6',
           'Haiti',
           'HTI',
           '341',
           '332',
           'HTI',
           'Haiti',
           'Haiti',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '55e14b18-f15a-564f-9928-7d9313dfdbe5',
           'Heard e ilhas mcdonald, Ilha',
           'HMD',
           '343',
           '334',
           'HMD',
           'Heard Island and McDonald Islands',
           'Islas Heard y McDonald',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b9c4c99e-86a6-5540-a8e9-60e1419dda2c',
           'Honduras',
           'HND',
           '345',
           '340',
           'HND',
           'Honduras',
           'Honduras',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1841638d-f245-5656-923f-b957c60edc0e',
           'Hong Kong',
           'HKG',
           '351',
           '344',
           'HKG',
           'Hong Kong',
           'Hong Kong',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '57905587-2f6b-5c1e-a25b-3ac17ec03679',
           'Hungria',
           'HUN',
           '355',
           '348',
           'HUN',
           'Hungary',
           'Hungría',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b411e6d4-7bbe-50f4-9d84-0aa270c3de85',
           'Iêmen',
           'YEM',
           '357',
           '887',
           'YEM',
           'Yemen',
           'Yemen',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '84f03f4b-c6bf-53af-97b4-cb378ec76646',
           'Iêmen Democrático',
           'YMD',
           '358',
           '720',
           'YMD',
           'Democratic Yemen',
           'Yemen Democrático',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c355b7d4-04ee-5774-95ec-888bf2334e1b',
           'Ilha de Man',
           'IMN',
           '359',
           '833',
           'IMN',
           'Isle of Man',
           'Isla de Man',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c76d8185-8c83-546f-a03b-5836ea655e85',
           'Índia',
           'IND',
           '361',
           '356',
           'IND',
           'India',
           'India',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '43f5ab8f-6801-50d0-8817-f73b72974a7e',
           'Indonésia',
           'IDN',
           '365',
           '360',
           'IDN',
           'Indonesia',
           'Indonesia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1e218f81-0d66-53a2-9c61-18982add48df',
           'Inglaterra',
           'GBR',
           '367',
           '826',
           'GBR',
           'England',
           'Inglaterra',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '84db812e-ad4c-5efd-85ff-37a525dc0dac',
           'Iraque',
           'IRQ',
           '369',
           '368',
           'IRQ',
           'Iraq',
           'Irak',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'df2a6281-422a-5602-8640-128ebd7c5a07',
           'Irã',
           'IRN',
           '372',
           '364',
           'IRN',
           'Iran',
           'Irán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4c745cf7-707d-5c16-9527-56b8fbb21eac',
           'Irlanda',
           'IRL',
           '375',
           '372',
           'IRL',
           'Ireland',
           'Irlanda',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b048d832-04f6-5cd0-bffa-83fce1d2d830',
           'Islândia',
           'ISL',
           '379',
           '352',
           'ISL',
           'Iceland',
           'Islandia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1c6b9cbc-2802-5454-bb87-3873cd5b6bf2',
           'Israel',
           'ISR',
           '383',
           '376',
           'ISR',
           'Israel',
           'Israel',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '99a895d1-ab5b-547c-b67e-3b8f0b098c05',
           'Itália',
           'ITA',
           '386',
           '380',
           'ITA',
           'Italy',
           'Italia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '44a42b7d-5f70-5d22-b430-0e19a372a2a3',
           'Iugoslávia',
           'SCG',
           '388',
           '891',
           'SCG',
           'Yugoslavia',
           'Yugoslavia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e7023855-a4f8-5858-9c7d-e8bcfbd843fd',
           'Jamaica',
           'JAM',
           '391',
           '388',
           'JAM',
           'Jamaica',
           'Jamaica',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Jersey',
           'ZZZ',
           '393',
           '898',
           'ZZZ',
           'Jersey',
           'Jersey',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '222fa0b6-f07b-5cd2-89f0-19dddae3cd10',
           'Johnston, Ilhas',
           'USA',
           '396',
           '840',
           'USA',
           'Johnston (Island)',
           'Johnston (Isla)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'fb5f3ddf-0f95-5a65-a3e6-2a828b858e35',
           'Japão',
           'JPN',
           '399',
           '392',
           'JPN',
           'Japan',
           'Japón',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'a796b075-5356-550b-bdf5-a2d42600b2c1',
           'Jordânia',
           'JOR',
           '403',
           '400',
           'JOR',
           'Jordan',
           'Jordania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '05947f51-265a-51a6-a998-f16e6974591a',
           'Kiribati',
           'KIR',
           '411',
           '296',
           'KIR',
           'Kiribati',
           'Kiribati',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '807420c2-911c-5023-a119-d3155d68a714',
           'Laos',
           'LAO',
           '420',
           '418',
           'LAO',
           'Laos',
           'Laos',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5ac483a7-c180-5a4c-8c01-dc1e8e529719',
           'Lebuan, Ilhas',
           'MYS',
           '423',
           '458',
           'MYS',
           'Lebuan',
           'Lebuan',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '62184d87-9dd5-5362-b0d7-5f17350f091c',
           'Lesoto',
           'LSO',
           '426',
           '426',
           'LSO',
           'Lesotho',
           'Lesotho',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '8654b831-0e3d-5c53-a8ec-cabb52f077ed',
           'Letônia',
           'LVA',
           '427',
           '428',
           'LVA',
           'Latvia',
           'Letonia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'fbb62791-0eca-5cf8-bd5e-bbf0e1c9c30f',
           'Líbano',
           'LBN',
           '431',
           '422',
           'LBN',
           'Lebanon',
           'Líbano',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '78b77e46-16e8-59a4-97ec-005740abc7c9',
           'Libéria',
           'LBR',
           '434',
           '430',
           'LBR',
           'Liberia',
           'Liberia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9d8e3a52-051a-5287-bb0e-edb9ebfc0355',
           'Líbia',
           'LBY',
           '438',
           '434',
           'LBY',
           'Libya',
           'Libia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6d630ee1-8cc1-5a12-8994-b239f07f7dec',
           'Liechtenstein',
           'LIE',
           '440',
           '438',
           'LIE',
           'Liechtenstein',
           'Liechtenstein',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '18eadb0d-f138-586e-92fd-09a1f0cc5afa',
           'Lituânia',
           'LTU',
           '442',
           '440',
           'LTU',
           'Lithuania',
           'Lituania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5675a31b-c585-5133-a436-4833f66834f0',
           'Luxemburgo',
           'LUX',
           '445',
           '442',
           'LUX',
           'Luxembourg',
           'Luxemburgo',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '66b7074f-c72c-5573-b4fd-d7f8076ac74d',
           'Macau',
           'MAC',
           '447',
           '446',
           'MAC',
           'Macao',
           'Macao',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '795d0d71-3887-5c98-8c68-8e3f9684cc5f',
           'Macedônia',
           'MKD',
           '449',
           '807',
           'MKD',
           'Macedonia',
           'Macedonia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5f58589f-bc4a-508f-a467-266bd17aa407',
           'Madagascar',
           'MDG',
           '450',
           '450',
           'MDG',
           'Madagascar',
           'Madagascar',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e04ba535-5b9b-53bb-94f6-102813faf495',
           'Madeira, Ilha da',
           'PRT',
           '452',
           '620',
           'PRT',
           'Madeira',
           'Madeira',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5ac483a7-c180-5a4c-8c01-dc1e8e529719',
           'Malásia',
           'MYS',
           '455',
           '458',
           'MYS',
           'Malaysia',
           'Malasia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7dd0fecb-6ba6-5539-a9df-f70606cfe9fa',
           'Malavi',
           'MWI',
           '458',
           '454',
           'MWI',
           'Malawi',
           'Malawi',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4218d63d-8208-5a72-8057-ab8fa3ad0071',
           'Maldivas',
           'MDV',
           '461',
           '462',
           'MDV',
           'Maldives',
           'Maldivas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2fbb9215-1181-5ac7-82d0-51cdf0dee3ef',
           'Mali',
           'MLI',
           '464',
           '466',
           'MLI',
           'Mali',
           'Mali',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '64d62bb2-da11-5f4b-9789-f0e75d1b53d1',
           'Malta',
           'MLT',
           '467',
           '470',
           'MLT',
           'Malta',
           'Malta',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'fa2ed78f-9bac-5fa5-b41f-6890a6c7aac7',
           'Marianas do Norte, Ilhas',
           'MNP',
           '472',
           '580',
           'MNP',
           'Northern Marianas',
           'Marianas Septentrionales, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e3661258-165f-53a7-9ed6-2c52ebb00fa2',
           'Marrocos',
           'MAR',
           '474',
           '504',
           'MAR',
           'Morocco',
           'Marruecos',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '80898019-e770-53b2-875d-54d99bc39647',
           'Marshall, Ilhas',
           'MHL',
           '476',
           '584',
           'MHL',
           'Marshall Islands',
           'Islas Marshall',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'be9fae76-6bfc-5365-a3a7-40521f0eb4f9',
           'Martinica',
           'MTQ',
           '477',
           '474',
           'MTQ',
           'Martinique',
           'Martinica',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9aae72c8-0447-51a5-be2d-a40409966361',
           'Maurício',
           'MUS',
           '485',
           '480',
           'MUS',
           'Mauritius',
           'Mauricio',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '83069aee-6e21-53a9-abea-dc4c3e5a9a58',
           'Mauritânia',
           'MRT',
           '488',
           '478',
           'MRT',
           'Mauritania',
           'Mauritania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Mayotte',
           'ZZZ',
           '489',
           '898',
           'ZZZ',
           'Mayotte',
           'Mayotte',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e940f1a3-3f0e-59de-a847-5a958548fe0c',
           'Midway, Ilhas',
           'UMI',
           '490',
           '581',
           'UMI',
           'Midway, Islands',
           'Midways, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd83dbc81-8d20-546e-bf19-890195617520',
           'México',
           'MEX',
           '493',
           '484',
           'MEX',
           'Mexico',
           'México',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '04966384-aa60-5e95-9059-6a6a8f154b86',
           'Moldávia',
           'MDA',
           '494',
           '498',
           'MDA',
           'Moldova',
           'Moldova, República de',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '18b47c3b-a142-590d-890b-809bfb9db1af',
           'Mônaco',
           'MCO',
           '495',
           '492',
           'MCO',
           'Monaco',
           'Mónaco',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'eca05ae8-1a79-5de2-adf9-7c2af8c98739',
           'Mongólia',
           'MNG',
           '497',
           '496',
           'MNG',
           'Mongolia',
           'Mongolia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4f81619d-725f-5ff4-bbc1-12b3201f591a',
           'Montenegro',
           'MNE',
           '498',
           '499',
           'MNE',
           'Montenegro',
           'Montenegro',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '02475c21-27e9-53b5-8569-dee48480bcca',
           'Micronésia',
           'FSM',
           '499',
           '583',
           'FSM',
           'Micronesia',
           'Micronesia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '65570ba7-7a7d-5783-85f3-c854abf0de36',
           'Montserrat',
           'MSR',
           '501',
           '500',
           'MSR',
           'Montserrat',
           'Montserrat',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '41559803-81ae-58bd-801b-e9885c302432',
           'Moçambique',
           'MOZ',
           '505',
           '508',
           'MOZ',
           'Mozambique',
           'Mozambique',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9d0ea3fb-e966-52a4-9554-362d740cf2f7',
           'Namíbia',
           'NAM',
           '507',
           '516',
           'NAM',
           'Namibia',
           'Namibia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'abe9086a-e524-5a0b-86d1-488d9e835433',
           'Nauru',
           'NRU',
           '508',
           '520',
           'NRU',
           'Nauru',
           'Nauru',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '856700db-a13e-5d1c-8558-6180333a322f',
           'Christmas (Navidad), Ilha',
           'CXR',
           '511',
           '162',
           'CXR',
           'Christmas Island',
           'Navidad (Christmas), Isla',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '09922964-bba9-5200-81e4-2c925155b77c',
           'Nepal',
           'NPL',
           '517',
           '524',
           'NPL',
           'Nepal',
           'Nepal',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '94aadce5-1ac2-538f-a1e5-0f0dcf4ca7e7',
           'Nicarágua',
           'NIC',
           '521',
           '558',
           'NIC',
           'Nicaragua',
           'Nicaragua',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '37205669-a16e-5d6f-ac29-724ddfc9c3e7',
           'Níger',
           'NER',
           '525',
           '562',
           'NER',
           'Niger',
           'Níger',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '91593378-69bb-5ff6-af0d-bdf45b728dec',
           'Nigéria',
           'NGA',
           '528',
           '566',
           'NGA',
           'Nigeria',
           'Nigeria',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e4844235-c464-51ad-9216-293cf1d64304',
           'Niue',
           'NIU',
           '531',
           '570',
           'NIU',
           'Niue',
           'Niue',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '170d7d9d-eb47-5cb0-b230-2141241efae6',
           'Norfolk, Ilha',
           'NFK',
           '535',
           '574',
           'NFK',
           'Norfolk Island',
           'Norfolk, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1387be74-3eaa-52c8-90b0-f04973662999',
           'Noruega',
           'NOR',
           '538',
           '578',
           'NOR',
           'Norway',
           'Noruega',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '92cb17b5-6ec4-5feb-88e3-a815f5c2dfa4',
           'Nova Caledônia',
           'NCL',
           '542',
           '540',
           'NCL',
           'New Caledonia',
           'Nueva Caledonia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '55b2ad00-c181-5d18-a157-2e7a471717c7',
           'Papua Nova Guiné',
           'PNG',
           '545',
           '598',
           'PNG',
           'Papua New Guinea',
           'Papua Nueva Guinea',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f0541f62-6230-5323-aeca-ec526bb856fd',
           'Nova Zelândia',
           'NZL',
           '548',
           '554',
           'NZL',
           'New Zealand',
           'Nueva Zelanda',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '17982af2-b872-5342-a1f8-2d3c81c9093f',
           'Vanuatu',
           'VUT',
           '551',
           '548',
           'VUT',
           'Vanuatu',
           'Vanuatu',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '39651a49-dd98-5499-8673-7817019d5503',
           'Omã',
           'OMN',
           '556',
           '512',
           'OMN',
           'Oman',
           'Omán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e940f1a3-3f0e-59de-a847-5a958548fe0c',
           'Pacífico, Ilhas do (Administração dos EUA)',
           'UMI',
           '563',
           '581',
           'UMI',
           'Pacific, Islands of (U.S. Administration)',
           'Pacífico, las Islas (EE.UU administración)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e940f1a3-3f0e-59de-a847-5a958548fe0c',
           'Pacífico, Ilhas do (EUA)',
           'UMI',
           '566',
           '581',
           'UMI',
           'Pacific Islands (USA)',
           'Pacífico, Islas del (Estados Unidos)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e940f1a3-3f0e-59de-a847-5a958548fe0c',
           'Pacífico, Ilhas do (Território Fideicomisso EUA)',
           'UMI',
           '569',
           '581',
           'UMI',
           'Pacifics, Islands of (U.S. trust territory)',
           'Pacífico, las islas (EE.UU. TERRITORIO EN FIDEICOMISO',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '3cb3be95-9a12-5b1f-b8f0-a2d7d1e28caa',
           'Países Baixos (Holanda)',
           'NLD',
           '573',
           '528',
           'NLD',
           'Netherlands',
           'Países Bajos',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b95a620f-8d86-59f1-bc05-466c0f6c8583',
           'Palau',
           'PLW',
           '575',
           '585',
           'PLW',
           'Palau',
           'Palau',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '15459373-a741-523d-8ca3-f8d337b54ece',
           'Paquistão',
           'PAK',
           '576',
           '586',
           'PAK',
           'Pakistan',
           'Pakistán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '41d24e25-9c28-52fc-a744-2908ec1d21b3',
           'Palestina',
           'PSE',
           '578',
           '275',
           'PSE',
           'Palestine',
           'Palestina',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '85373e9d-da1f-582c-b38e-0f5f1f531b6a',
           'Panamá',
           'PAN',
           '580',
           '591',
           'PAN',
           'Panama',
           'Panamá',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '55b2ad00-c181-5d18-a157-2e7a471717c7',
           'Papua, Território de',
           'PNG',
           '583',
           '598',
           'PNG',
           'Papua territory',
           'Territorio de Papua',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7e71cde1-afbf-5c79-888f-9e7be5951c2d',
           'Paraguai',
           'PRY',
           '586',
           '600',
           'PRY',
           'Paraguay',
           'Paraguay',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bdbb530c-5392-53d2-adc4-abd0b9ec0bf5',
           'Peru',
           'PER',
           '589',
           '604',
           'PER',
           'Peru',
           'Perú',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c84272bd-1146-5d1b-beb8-9b515577d8cf',
           'Pitcairn',
           'PCN',
           '593',
           '612',
           'PCN',
           'Pitcairn',
           'Pitcairn',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c3f2bfcf-01ff-5109-9767-7e83294a2fef',
           'Polinésia Francesa',
           'PYF',
           '599',
           '258',
           'PYF',
           'French Polynesia',
           'Polinesia Francesa',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '941e7149-29f7-501b-86cd-2795c8094a03',
           'Polônia',
           'POL',
           '603',
           '616',
           'POL',
           'Poland',
           'Polonia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'e04ba535-5b9b-53bb-94f6-102813faf495',
           'Portugal',
           'PRT',
           '607',
           '620',
           'PRT',
           'Portugal',
           'Portugal',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'cd9100f2-dd92-58e3-9cf5-d8840030be89',
           'Porto Rico',
           'PRI',
           '611',
           '630',
           'PRI',
           'Puerto Rico',
           'Puerto Rico',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '333834c6-f41b-59f5-8dc8-c1e2dcfaba24',
           'Quênia',
           'KEN',
           '623',
           '404',
           'KEN',
           'Kenya',
           'Kenya',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2659af1d-30dc-5c6e-ac1e-b31a2c2df5af',
           'Quirguistão',
           'KGZ',
           '625',
           '417',
           'KGZ',
           'Kyrgyzstan',
           'Kirguistán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1e218f81-0d66-53a2-9c61-18982add48df',
           'Reino Unido',
           'GBR',
           '628',
           '826',
           'GBR',
           'United Kingdom',
           'Reino Unido',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '96c95cbc-40f1-5efd-a2bb-28a5ab028819',
           'República Centro-Africana',
           'CAF',
           '640',
           '140',
           'CAF',
           'Central African Republic',
           'República Centroafricana',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'ad4b0c24-9a3e-5af0-9fac-2ff3aa2d4de6',
           'República Dominicana',
           'DOM',
           '647',
           '214',
           'DOM',
           'Dominican Republic',
           'República Dominicana',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6311222d-e5ac-5a56-941c-444ad0e7234c',
           'Reunião',
           'REU',
           '660',
           '638',
           'REU',
           'Reunion',
           'Reunión',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '496296e1-168d-59b1-ab71-7270c2506b21',
           'Zimbábue',
           'ZWE',
           '665',
           '716',
           'ZWE',
           'Zimbabwe',
           'Zimbabwe',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd6a1e88e-a040-50c0-be3a-29cd88bda661',
           'Romênia',
           'ROU',
           '670',
           '642',
           'ROU',
           'Romania',
           'Rumania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '73e5c590-e834-5653-a25c-63949ceaa195',
           'Ruanda',
           'RWA',
           '675',
           '646',
           'RWA',
           'Rwanda',
           'Ruanda',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2c51f138-47e0-57cb-9e0f-6034f1a26ed0',
           'Rússia',
           'RUS',
           '676',
           '643',
           'RUS',
           'Russia',
           'Rusia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0b62580d-6f63-5e8e-bc34-f561340d7bb5',
           'Salomão, Ilhas',
           'SLB',
           '677',
           '090',
           'SLB',
           'Solomon Islands',
           'Salomón, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '93417ba8-9565-5d96-9864-324e6c61be82',
           'Saint Kitts e Nevis',
           'KNA',
           '678',
           '659',
           'KNA',
           'St. Kitts and Nevis',
           'St. Kitts y Nevis',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5b420a4e-f80d-526d-a8cb-2b4d152034bb',
           'Saara Ocidental',
           'ESH',
           '685',
           '732',
           'ESH',
           'Western Sahara',
           'Sahara Occidental',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '3043d777-5189-5b4a-b82e-70c6659bcc1e',
           'El Salvador',
           'SLV',
           '687',
           '222',
           'SLV',
           'El Salvador',
           'El Salvador',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '3b5c675b-0505-519f-a4c3-d86dfacc192d',
           'Samoa',
           'WSM',
           '690',
           '882',
           'WSM',
           'Samoa',
           'Samoa',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7a90a3a7-8784-5abe-b803-559ced385354',
           'Samoa Americana',
           'ASM',
           '691',
           '016',
           'ASM',
           'Americana Samoa',
           'Samoa Americana',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0e10bde6-e820-5232-bc0b-43f6ad576a67',
           'São Bartolomeu',
           'BLM',
           '693',
           '652',
           'BLM',
           'Saint Barthelemy',
           'San Bartolomé',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '93417ba8-9565-5d96-9864-324e6c61be82',
           'São Cristóvão e Névis',
           'KNA',
           '695',
           '659',
           'KNA',
           'Saint Christopher and Nevis',
           'Saint Kitts y Nevis',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'ccc8b2ff-750d-56f3-ab8e-7bedba780cb7',
           'San Marino',
           'SMR',
           '697',
           '674',
           'SMR',
           'San Marino',
           'San Marino',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'São Martinho, Ilha de (parte francesa)',
           'ZZZ',
           '698',
           '898',
           'ZZZ',
           'São Martinho, Ilha de (parte francesa)',
           'São Martinho, Ilha de (parte francesa)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7d094345-b0c1-5547-b4e1-0f12f173abbb',
           'Sint Maarten',
           'SXM',
           '699',
           '534',
           'SXM',
           'Sint Maarten',
           'Sint Maarten',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '00a23d61-fb25-5010-ad60-d69d231d4b56',
           'São Pedro e Miquelon',
           'SPM',
           '700',
           '666',
           'SPM',
           'Saint Pierre and Miquelon',
           'Saint Pierre y Miquelon',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f07f9420-0e10-5c86-8421-000599fdb553',
           'São Vicente e Granadinas',
           'VCT',
           '705',
           '670',
           'VCT',
           'Saint Vicent and the Granadines',
           'San Vicente y las Granadinas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '845fe7cf-8a8d-53f6-9412-75464e73f4f7',
           'Santa Helena',
           'SHN',
           '710',
           '654',
           'SHN',
           'Saint Helena',
           'Santa Helena, Ascención y Tristán de Acuña',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '43ed2734-a6f6-5706-b157-b49316af0418',
           'Santa Lúcia',
           'LCA',
           '715',
           '662',
           'LCA',
           'Saint Lucia',
           'Santa Lucia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f7ad9b65-ea7b-5119-8cdf-a9e1afb80853',
           'São Tomé e Príncipe',
           'STP',
           '720',
           '678',
           'STP',
           'São Tome and Principe',
           'Santo Tomé y Príncipe',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f10f06e7-955c-54c0-b89e-d62783354c45',
           'Senegal',
           'SEN',
           '728',
           '686',
           'SEN',
           'Senegal',
           'Senegal',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd3d64e74-2051-52e2-9c21-c05f2b3008b1',
           'Seicheles',
           'SYC',
           '731',
           '690',
           'SYC',
           'Seychelles',
           'Seychelles',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f1b3a44e-2a8b-5e7c-affe-d3e7251a4f48',
           'Serra Leoa',
           'SLE',
           '735',
           '694',
           'SLE',
           'Sierra Leone',
           'Sierra Leona',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '85544719-085d-597a-9139-e02e8767122d',
           'Sérvia',
           'SRB',
           '737',
           '688',
           'SRB',
           'Serbia',
           'Serbia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'b43c4845-4ac0-5631-9252-7c2b1c37e989',
           'Singapura',
           'SGP',
           '741',
           '702',
           'SGP',
           'Singapore',
           'Singapur',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9ffd1c50-d130-5dbe-8560-ac41fdc9f652',
           'Síria',
           'SYR',
           '744',
           '760',
           'SYR',
           'Syria',
           'Siria',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '88b4f9de-2c03-5755-86e8-7e3e7d9f8477',
           'Somália',
           'SOM',
           '748',
           '706',
           'SOM',
           'Somalia',
           'Somalia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '77a9063c-e2a2-570f-ab28-4782337a4cc6',
           'Sri Lanka',
           'LKA',
           '750',
           '144',
           'LKA',
           'Sri Lanka',
           'Sri Lanka',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f43e6dff-040b-52d2-9831-e75b7b186388',
           'Suazilândia',
           'SWZ',
           '754',
           '748',
           'SWZ',
           'Swaziland',
           'Swazilandia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0e8954f6-adf7-5ee4-85d1-b3d17bef2189',
           'Svalbard e Jan Mayen',
           'SJM',
           '755',
           '744',
           'SJM',
           'Svalbard and Jan Mayen',
           'Svalbard y Jan Mayen',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'd6af735f-292e-5e5e-8fda-36ad5d900f92',
           'África do Sul',
           'ZAF',
           '756',
           '710',
           'ZAF',
           'South Africa',
           'Sudáfrica',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c9d33e09-4a86-5ce0-b32c-0c6ba10f2719',
           'Sudão',
           'SDN',
           '759',
           '736',
           'SDN',
           'Sudan',
           'Sudán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '52d94e14-d95c-56f5-939c-d01fbf7d9c5a',
           'Sudão do Sul',
           'SSD',
           '760',
           '728',
           'SSD',
           'South Sudan',
           'Sudán del Sur',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '37619dfd-ae98-54d9-982d-d5d8c5809a3d',
           'Suécia',
           'SWE',
           '764',
           '752',
           'SWE',
           'Sweden',
           'Suecia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9a02ea0e-e8fb-5bb8-beab-56c8afb41ed2',
           'Suíça',
           'CHE',
           '767',
           '756',
           'CHE',
           'Switzerland',
           'Suiza',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5011bbac-56d5-5e2e-9248-abd55bdd59d5',
           'Suriname',
           'SUR',
           '770',
           '740',
           'SUR',
           'Suriname',
           'Suriname',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '0f07cbc6-7188-5f6d-aa62-32142d6b67d2',
           'Tadjiquistão',
           'TJK',
           '772',
           '762',
           'TJK',
           'Tajikistan',
           'Tayikistán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '11027e7f-bfa8-518e-8204-18b32216ae21',
           'Tailândia',
           'THA',
           '776',
           '764',
           'THA',
           'Thailand',
           'Tailandia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '29dea6a9-13ea-5768-b830-8382f9757de2',
           'Tanzânia',
           'TZA',
           '780',
           '834',
           'TZA',
           'Tanzania',
           'Tanzanía',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bef16947-cacb-5abe-8ee2-78a6ad55aee7',
           'Terras Austrais Francesas',
           'ATF',
           '781',
           '898',
           'ATF',
           'French Southern Lands',
           'Tierras Australes Francesas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '5c5d9dba-5457-56d1-8cbe-e8d7be3544f5',
           'Território Britânico do Oceano Índico',
           'IOT',
           '782',
           '086',
           'IOT',
           'British Indian Ocean Territory',
           'Territorio Británico del Océano Indico',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'beb5e41c-f471-57fa-a64d-bb80c0e59ed5',
           'Djibuti',
           'DJI',
           '783',
           '262',
           'DJI',
           'Djibouti',
           'Djibouti',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Território da Alta Comissão do Pacífico Ocidental',
           'ZZZ',
           '785',
           '898',
           'ZZZ',
           'Territory of the High Commissioner of the Western Pacif',
           'Territorio del Alto Comisionado del Pacífico Occidental',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1e218f81-0d66-53a2-9c61-18982add48df',
           'Território Antártico Britânico',
           'GBR',
           '786',
           '826',
           'GBR',
           'British Antarctic Territory',
           'Territorio Antártico Británico',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7498a4fe-61cd-564e-a633-2918faf776f9',
           'Chade',
           'TCD',
           '788',
           '148',
           'TCD',
           'Chad',
           'Chad',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '829df71e-8fbb-5b52-a255-c15b85652c7f',
           'Tchecoslováquia',
           'CSK',
           '790',
           '200',
           'CSK',
           'Czechoslovakia',
           'Checoslovaquia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f9483674-fe33-518f-96e3-1a8918b44521',
           'Tcheca, República',
           'CZE',
           '791',
           '203',
           'CZE',
           'Czech Republic',
           'República Checa',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '1cdf042c-a6e0-576e-80f0-fc65b908ffc3',
           'Timor Leste',
           'TLS',
           '795',
           '626',
           'TLS',
           'East Timor',
           'Timor Leste',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '412b5f6e-801a-5273-803c-f6ddf9ec5863',
           'Togo',
           'TGO',
           '800',
           '768',
           'TGO',
           'Togo',
           'Togo',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '10f4c99a-6779-50a8-966f-31c8a0c74672',
           'Toquelau',
           'TKL',
           '805',
           '772',
           'TKL',
           'Tokelau',
           'Tokelau',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '83d1dd04-7bbf-5a30-bf13-947a66057046',
           'Tonga',
           'TON',
           '810',
           '776',
           'TON',
           'Tonga',
           'Tonga',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'daf06bae-e889-5086-a374-6f4739d26909',
           'Trinidad e Tobago',
           'TTO',
           '815',
           '780',
           'TTO',
           'Trinidad and Tobago',
           'Trinidad y Tobago',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '2ea86749-7735-5d70-bf7f-8042b699ed9a',
           'Tunísia',
           'TUN',
           '820',
           '788',
           'TUN',
           'Tunisia',
           'Túnez',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '68374b3e-d7b9-5566-8464-3adb28aa4043',
           'Turcas e Caicos, Ilhas',
           'TCA',
           '823',
           '796',
           'TCA',
           'Turks and Caicos, Islands',
           'Turcas y Caicos, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '52264112-83ac-5576-b813-13dda7f69fda',
           'Turcomenistão',
           'TKM',
           '824',
           '795',
           'TKM',
           'Turkmenistan',
           'Turkmenistán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f4a6ea88-d6b8-5dc3-962d-e1e729e1e8bf',
           'Turquia',
           'TUR',
           '827',
           '792',
           'TUR',
           'Turkey',
           'Turquía',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '316b824d-d338-5045-8cf1-bbf324b0306c',
           'Tuvalu',
           'TUV',
           '828',
           '798',
           'TUV',
           'Tuvalu',
           'Tuvalu',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4fa228c7-623d-5e30-b0be-b1bbcf67ef85',
           'Ucrânia',
           'UKR',
           '831',
           '804',
           'UKR',
           'Ukraine',
           'Ucrania',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4d578eef-7931-5acb-9c3b-a5c05c4c4775',
           'Uganda',
           'UGA',
           '833',
           '800',
           'UGA',
           'Uganda',
           'Uganda',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'União das Repúblicas Socialistas Soviéticas',
           'ZZZ',
           '840',
           '898',
           'ZZZ',
           'Union of Soviet Socialist Republics',
           'Unión de Repúblicas Socialistas Soviéticas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6f6e6db0-adf9-55ab-a15d-a8ce2aedd119',
           'Uruguai',
           'URY',
           '845',
           '858',
           'URY',
           'Uruguay',
           'Uruguay',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'dbd4466e-a513-5e3f-9c8e-1787674020e0',
           'Uzbequistão',
           'UZB',
           '847',
           '860',
           'UZB',
           'Uzbekistan',
           'Uzbekistán',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '7b42e804-cec3-5074-9a89-1b2f4d4cff1e',
           'Vaticano',
           'VAT',
           '848',
           '336',
           'VAT',
           'Vatican City',
           'Santa Sede (Vaticano)',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'fd4c8be7-7d23-5369-9ca5-64eecfcb0f47',
           'Venezuela',
           'VEN',
           '850',
           '862',
           'VEN',
           'Venezuela',
           'Venezuela',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '6da04a00-f32f-5956-929a-365dc8e80158',
           'Vietnã',
           'VNM',
           '858',
           '704',
           'VNM',
           'Vietnam',
           'Viet Nam',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '4bc2b546-7ebd-5ac4-b7dd-485cc47efc9b',
           'Virgens, Ilhas (Britânicas)',
           'VGB',
           '863',
           '092',
           'VGB',
           'Virgin Islands (UK)',
           'Vírgenes Británicas, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '91c7e630-90b0-5936-9ac5-aaab358f52cc',
           'Virgens, Ilhas (Americanas)',
           'VIR',
           '866',
           '850',
           'VIR',
           'Virgin Islands (USA)',
           'Vírgenes de los Estados Unidos, Islas',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c331c125-95ff-5109-971e-0d99dabe58a8',
           'Fiji',
           'FJI',
           '870',
           '242',
           'FJI',
           'Fiji',
           'Fiji',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '222fa0b6-f07b-5cd2-89f0-19dddae3cd10',
           'Wake, Ilha',
           'USA',
           '873',
           '840',
           'USA',
           'Wake Island',
           'Isla Wake',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'eca87498-0226-54ea-9a7c-7f0ca3d41f13',
           'Wallis e Futuna, Ilhas',
           'WLF',
           '875',
           '876',
           'WLF',
           'Wallis and Futuna',
           'Wallis y Futuna',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           '9ccb538d-3a51-5e21-bafe-c3ad2602c194',
           'Congo, República Democrática',
           'COD',
           '888',
           '180',
           'COD',
           'Democratic Republic of the Congo',
           'República Democrática del Congo',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'f0589742-68a2-5982-9c9b-e8301bcaa993',
           'Zâmbia',
           'ZMB',
           '890',
           '894',
           'ZMB',
           'Zambia',
           'Zambia',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'c4bb2480-b027-5c56-a590-00defb1e233a',
           'Zona do Canal do Panamá',
           'PCZ',
           '895',
           '592',
           'PCZ',
           'Panama Canal Zone',
           'Zona del Canal de Panamá',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Provisão de Navios e Aeronaves',
           'ZZZ',
           '990',
           '898',
           'ZZZ',
           'Planes and ships provisions',
           'Disposiciones para buques y aviones',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'A Designar',
           'ZZZ',
           '994',
           '898',
           'ZZZ',
           'To define',
           'A designar',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Bancos Centrais',
           'ZZZ',
           '995',
           '898',
           'ZZZ',
           'Central Banks',
           'Bancos Centrales',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Organizações Internacionais',
           'ZZZ',
           '997',
           '898',
           'ZZZ',
           'International Organizations',
           'Organizaciones Internacionales',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Sem informação',
           'ZZZ',
           '998',
           '898',
           'ZZZ',
           'Sem informação',
           'Sem informação',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;

INSERT INTO country (
    id,
    name,
    abbreviation,
    country_code,
    iso_n3_code,
    iso_a3_code,
    name_en,
    name_es,
    created_at,
    version
)
VALUES (
           'bcfea0df-8da4-5148-8091-f8533f7fb77b',
           'Não Declarados',
           'ZZZ',
           '999',
           '898',
           'ZZZ',
           'Not declared',
           'No declarados',
           CURRENT_TIMESTAMP,
           0
       )
    ON CONFLICT (iso_a3_code) DO UPDATE
                                     SET
                                         name = EXCLUDED.name,
                                     abbreviation = EXCLUDED.abbreviation,
                                     country_code = EXCLUDED.country_code,
                                     iso_n3_code = EXCLUDED.iso_n3_code,
                                     name_en = EXCLUDED.name_en,
                                     name_es = EXCLUDED.name_es,
                                     updated_at = CURRENT_TIMESTAMP;
