CREATE TABLE Users
(
    id       UUID    NOT NULL PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    role     VARCHAR NOT NULL
);

CREATE TABLE Tokens
(
    id          UUID                     NOT NULL PRIMARY KEY,
    user_id     UUID                     NOT NULL,
    token_value UUID                     NOT NULL,
    valid_until TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE Games
(
    id   UUID    NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE User_Games
(
    user_id UUID,
    game_id UUID
);