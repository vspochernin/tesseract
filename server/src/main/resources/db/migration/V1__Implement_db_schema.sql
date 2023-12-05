-- Скрипт создания базы данных для Android приложения Tesseract
-- Создание таблиц
-- Создаем таблицу компаний
CREATE TABLE companies
(
    id                  SERIAL PRIMARY KEY,                -- автоувеличение
    title               VARCHAR(100)             NOT NULL, -- проанализировал - сейчас максимум 80, но возьмем с запасом
    description         VARCHAR(400)             NOT NULL, -- если мы тут полностью будем хранить всю краткую информацию
    foundation_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    revenue             BIGINT                   NOT NULL,
    profit              BIGINT                   NOT NULL,
    staff               INTEGER                  NOT NULL
);

-- Создаем таблицу для продуктов активов
CREATE TABLE assets
(
    id               SERIAL PRIMARY KEY,                                 -- автоувеличение
    title            VARCHAR(30)              NOT NULL,                  -- проанализировал - сейчас максимум 11, но возьмем с запасом
    description      VARCHAR(400)             NOT NULL,                  -- если мы тут полностью будем хранить всю краткую информацию
    release_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    company_id       INTEGER                  NOT NULL,
    interest         NUMERIC                  NOT NULL,                  -- процент доходности
    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE -- когда пропадает
    -- компания, должен и актив пропадать, иначе кто его выпустил.
    -- но можно сделать - ON DELETE SET NULL
);

-- Создаем таблицу цен активов
CREATE TABLE prices
(
    id           SERIAL PRIMARY KEY, -- автоувеличение
    asset_id     INTEGER                  NOT NULL,
    price        INTEGER                  NOT NULL,
    set_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE CASCADE
);

-- Создаем таблицу пользователей
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,   -- автоувеличение
    login    VARCHAR(16) NOT NULL, -- максимальная длина логина
    email    VARCHAR,              -- у нас в ФС не сказано
    password VARCHAR(60) NOT NULL  -- длина bcrypt
);

-- Создаем таблицу связи пользователей и активов
CREATE TABLE users_assets
(
    user_id  INTEGER NOT NULL,
    asset_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, asset_id),
    FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

-- Создаем таблицу диверсификаций
CREATE TABLE diversifications
(
    id              SERIAL PRIMARY KEY,                -- автоувеличение
    user_id         INTEGER                  NOT NULL,
    create_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    risk_type_id    INTEGER                  NOT NULL,
    amount          INTEGER                  NOT NULL, -- общая стоимость диверсификации я полагаю
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
    -- можно и каскадное сделать
);

-- Создаем таблицу связи диверсификаций и активов
CREATE TABLE diversifications_assets
(
    id                 SERIAL PRIMARY KEY, -- автоувеличение
    diversification_id INTEGER NOT NULL,
    asset_id           INTEGER NOT NULL,
    count              INTEGER NOT NULL,
    FOREIGN KEY (diversification_id) REFERENCES diversifications (id) ON DELETE SET NULL,
    FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE SET NULL
    -- можно и каскадное сделать
);
