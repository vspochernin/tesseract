-- Скрипт создания базы данных для Android приложения Tesseract
-- Создание таблиц
-- Создаем таблицу компаний
CREATE TABLE companies(
    id SERIAL PRIMARY KEY,  -- автоувеличение
    title VARCHAR(100) NOT NULL,  -- проанализировал - сейчас максимум 80, но возьмем с запасом
    description VARCHAR(400),  -- если мы тут полностью будем хранить всю краткую информацию
    foundation_datetime TIMESTAMP,
    revenue BIGINT,
    profit BIGINT,
    staff INT
);

-- Создаем таблицу для продуктов активов
CREATE TABLE assets(
    id SERIAL PRIMARY KEY,  -- автоувеличение
	title VARCHAR(30) NOT NULL,  -- проанализировал - сейчас максимум 11, но возьмем с запасом
	description VARCHAR(400),  -- если мы тут полностью будем хранить всю краткую информацию
	release_datetime TIMESTAMP,
	company_id INTEGER NOT NULL,
	interest NUMERIC NOT NULL,  -- процент доходности
	FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE -- когда пропадает
	-- компания, должен и актив пропадать, иначе кто его выпустил.
	-- но можно сделать - ON DELETE SET NULL
);

-- Создаем таблицу цен активов
CREATE TABLE prices(
    id SERIAL PRIMARY KEY,  -- автоувеличение
    asset_id INTEGER NOT NULL,
    price INTEGER NOT NULL,  -- не numeric??
	set_datetime TIMESTAMP NOT NULL,
	FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE CASCADE
);

-- Создаем таблицу описания рисков
CREATE TABLE risks(
    id SERIAL PRIMARY KEY,  -- автоувеличение
    description VARCHAR(100) NOT NULL  -- если кратко без пояснений писать тип рискованности - 25 хватит
);

-- Создаем таблицу пользователей
CREATE TABLE users(
    id SERIAL PRIMARY KEY,  -- автоувеличение
    login VARCHAR(16) NOT NULL,  -- максимальная длина логина
	email VARCHAR(320) NOT NULL,  -- у нас в ФС не сказано, максимум возможен 320
	password VARCHAR(130) NOT NULL -- длина sha-512
);

-- Создаем таблицу связи пользователей и активов
CREATE TABLE users_assets(
    user_id INTEGER NOT NULL,
	asset_id INTEGER NOT NULL,
	FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE SET NULL,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
	-- можно и каскадное сделать
	-- по-хорошему сюда еще PK нужен
);

-- Создаем таблицу диверсификаций
CREATE TABLE diversifications(
    id SERIAL PRIMARY KEY,  -- автоувеличение
	user_id INTEGER NOT NULL,
	create_datetime TIMESTAMP,
	risk_id INTEGER NOT NULL,
	amount INTEGER,  -- общая стоимость диверсификации я полагаю
	FOREIGN KEY (risk_id) REFERENCES risks (id) ON DELETE SET NULL,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
	-- можно и каскадное сделать
);

-- Создаем таблицу связи диверсификаций и активов
CREATE TABLE diversifications_assets(
    diversification_id INTEGER NOT NULL,
	asset_id INTEGER NOT NULL,
	count INTEGER,
	FOREIGN KEY (diversification_id) REFERENCES diversifications (id) ON DELETE SET NULL,
	FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE SET NULL
	-- можно и каскадное сделать
	-- по-хорошему еще PK нужен
);
