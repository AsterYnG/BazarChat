
INSERT INTO "user"(id,login,password,user_pic,role_id,online,email,name,surname,nickname)
VALUES (1,'test','test',null,1,true,'khud@mail.ru','test','test','test');



INSERT INTO message(id,user_id,date,message)
VALUES (1,1, current_date,'hello'),
        (2,1, current_date,'it'),
        (3,1, current_date,'fuck'),
        (4,1, current_date,'no'),
        (5,1, current_date,'shit'),
        (6,1, current_date,'holy'),
        (7,1, current_date,'mom'),
        (8,1, current_date,'dad'),
        (9,1, current_date,'fuck'),
        (10,1, current_date,'cringe'),
        (11,1, current_date,'lol'),
        (12,1, current_date,'man'),
        (13,1, current_date,'female');

SELECT SETVAL('message_id_seq', (SELECT MAX(id) FROM message));

