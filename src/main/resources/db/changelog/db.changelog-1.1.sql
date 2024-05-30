--liquibase formatted sql


--changeset ssc_tuatara:1
INSERT INTO role (role_name,role_name_ru)
VALUES ('USER','Лох'),('ADMIN','Бог');
