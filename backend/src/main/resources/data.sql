INSERT INTO Users (id, username, password, role)
VALUES ('f73ca34c-e3eb-4097-9047-b4bb97807be6', 'user1', 'password1', 'USER'),
       ('e1568beb-7198-4ab0-b4c9-329174bbd7d9', 'user2', 'password2', 'USER'),
       ('a0f67bc0-8130-451b-9339-e077a0ab1576', 'user3', 'password3', 'USER'),
       ('1b2ef8a7-ea35-42d8-8add-4e626e39e1c5', 'user4', 'password4', 'USER'),
       ('d23ca34c-e3eb-4097-9047-b4bb97807be6', 'admin', 'admin', 'ADMIN');

INSERT INTO Games (id, name, type, global_round, round_limit)
VALUES ('123ca34c-e3eb-4097-9047-b4bb97807bdd', 'game1', 'Standard', 1, 10);

INSERT INTO User_Games(user_id, game_id)
VALUES ('f73ca34c-e3eb-4097-9047-b4bb97807be6', '123ca34c-e3eb-4097-9047-b4bb97807bdd');

INSERT INTO Rounds(id, user_id, game_id, round)
VALUES ('789ca34c-e3eb-4097-9047-b4bb97807bdd', 'f73ca34c-e3eb-4097-9047-b4bb97807be6',
        '123ca34c-e3eb-4097-9047-b4bb97807bdd', 1);

INSERT INTO Companies(id, user_id, game_id, name, mission, initial_funds, funds)
VALUES ('867f9c2f-dd8f-46f4-80e3-ab4cfa9efd81', 'f73ca34c-e3eb-4097-9047-b4bb97807be6',
        '123ca34c-e3eb-4097-9047-b4bb97807bdd', null, null, 10000.0, 10000.0);

INSERT INTO Emails (id, company_id, _month, source, topic, message, opened)
VALUES ('4321a34c-e3eb-4097-9047-b4bb97807bdd', '867f9c2f-dd8f-46f4-80e3-ab4cfa9efd81', 'STYCZEN', 'Source', 'Topic',
        'Message', false);
