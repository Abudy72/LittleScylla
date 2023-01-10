CREATE TABLE verified_player(
    uid bigint NOT NULL PRIMARY KEY,
    ign varchar(70) NOT NULL,
    platform varchar(70) NOT NULL,
    account_date date NOT NULL,
    date_verified date NOT NULL default current_date,
    highest_mmr integer,
    total_hours integer,
    verified_by bigint NOT NULL default 0
);