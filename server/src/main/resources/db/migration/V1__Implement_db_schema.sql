CREATE TABLE users
(
    id       SERIAL4,
    login    VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE secrets
(
    id     SERIAL4,
    secret VARCHAR(255)
);