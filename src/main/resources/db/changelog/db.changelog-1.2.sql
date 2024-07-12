--liquibase formatted sql

--changeset ssc_tuatara:1
CREATE TABLE IF NOT EXISTS friend_status(
  id SERIAL PRIMARY KEY,
  status VARCHAR(64) NOT NULL
);

--changeset ssc_tuatara:2
CREATE TABLE IF NOT EXISTS friend(
    id SERIAL PRIMARY KEY ,
    owner_id INT NOT NULL,
    friend_id INT NOT NULL,
    status_id INT REFERENCES friend_status(id) NOT NULL,
    submitted_date TIMESTAMP
);

--changeset ssc_tuatara:3
ALTER TABLE friend
ADD CONSTRAINT un UNIQUE (owner_id,friend_id);


--changeset ssc_tuatara:4
INSERT INTO friend_status(status)
VALUES ('Создана'),
       ('На подтверждении'),
       ('Исполнена');