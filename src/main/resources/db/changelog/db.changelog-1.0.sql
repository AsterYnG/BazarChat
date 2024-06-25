--liquibase formatted sql

--changeset ssc_tuatara:1
CREATE TABLE IF NOT EXISTS role(
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(64) UNIQUE NOT NULL,
    role_name_ru VARCHAR(64) UNIQUE NOT NULL
);
--changeset ssc_tuatara:2
CREATE TABLE IF NOT EXISTS "user"(
    id SERIAL PRIMARY KEY,
    login VARCHAR(128) UNIQUE NOT NULL ,
    password VARCHAR(256) NOT NULL,
    user_pic VARCHAR(256),
    role_id INT REFERENCES role(id) NOT NULL,
    online BOOLEAN NOT NULL,
    email VARCHAR(256),
    name VARCHAR(128) NOT NULL default '',
    surname VARCHAR(128) NOT NULL default '',
    nickname VARCHAR(128)
);

--changeset ssc_tuatara:3
CREATE TABLE IF NOT EXISTS message(
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES "user"(id),
    date TIMESTAMP DEFAULT now(),
    message VARCHAR(1024) NOT NULL
);


