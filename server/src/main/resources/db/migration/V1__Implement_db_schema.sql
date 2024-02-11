CREATE TABLE operators
(
    id                 SERIAL PRIMARY KEY,
    title              VARCHAR(150)             NOT NULL,
    inclusion_datetime TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE companies
(
    id                  SERIAL PRIMARY KEY,
    title               VARCHAR(100)             NOT NULL,
    description         VARCHAR(400)             NOT NULL,
    foundation_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    revenue             BIGINT                   NOT NULL,
    profit              BIGINT                   NOT NULL,
    staff               INTEGER                  NOT NULL
);

CREATE TABLE assets
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(30)              NOT NULL,
    description      VARCHAR(400)             NOT NULL,
    release_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    company_id       INTEGER                  NOT NULL,
    interest         NUMERIC                  NOT NULL, -- Процент доходности.
    operator_id      INTEGER                  NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE,
    FOREIGN KEY (operator_id) REFERENCES operators (id) ON DELETE CASCADE
);

CREATE TABLE prices
(
    id           BIGSERIAL PRIMARY KEY,
    asset_id     INTEGER                  NOT NULL,
    price        BIGINT                   NOT NULL,
    set_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(32) NOT NULL,
    email    VARCHAR(32) NOT NULL,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE users_assets
(
    user_id  INTEGER NOT NULL,
    asset_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, asset_id),
    FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE diversifications
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER                  NOT NULL,
    create_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    risk_type_id    INTEGER                  NOT NULL,
    amount          BIGINT                   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE diversifications_assets
(
    id                 SERIAL PRIMARY KEY,
    diversification_id INTEGER NOT NULL,
    asset_id           INTEGER NOT NULL,
    count              BIGINT  NOT NULL,
    FOREIGN KEY (diversification_id) REFERENCES diversifications (id) ON DELETE SET NULL,
    FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE SET NULL
);
