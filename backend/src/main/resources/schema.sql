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
    id           UUID    NOT NULL PRIMARY KEY,
    name         VARCHAR NOT NULL,
    type         VARCHAR NOT NULL,
    global_round INT     NOT NULL,
    round_limit  INT     NOT NULL
);

CREATE TABLE User_Games
(
    user_id UUID,
    game_id UUID
);

CREATE TABLE Rounds
(
    id      UUID NOT NULL PRIMARY KEY,
    user_id UUID,
    game_id UUID,
    round   INT  NOT NULL
);
CREATE TABLE Companies
(
    id      UUID NOT NULL PRIMARY KEY,
    user_id UUID,
    game_id UUID,
    name    INT,
    mission VARCHAR
);
CREATE TABLE Emails
(
    id         UUID    NOT NULL PRIMARY KEY,
    company_id UUID    NOT NULL,
    _month      VARCHAR NOT NULL,
    source     VARCHAR NOT NULL,
    topic      VARCHAR NOT NULL,
    message    VARCHAR NOT NULL,
    opened     BOOL    NOT NULL
);

