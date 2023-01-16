CREATE TABLE IF NOT EXISTS verified_player(
    discord_id bigint NOT NULL PRIMARY KEY,
    ign varchar(70) NOT NULL,
    platform varchar(70) NOT NULL,
    account_date varchar(30) NOT NULL,
    date_verified TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    highest_mmr integer,
    total_hours integer,
    verified_by varchar(20) NOT NULL default 'System',
    flagged_status boolean NOT NULL default FALSE
);