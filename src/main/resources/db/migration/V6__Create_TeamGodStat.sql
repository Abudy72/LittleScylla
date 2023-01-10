CREATE TABLE team_god_stat
(
    god_name      varchar(150) not null,
    team_name     varchar(70)  not null,
    picks         int          not null default 0,
    bans          int          not null default 0,
    wins          int          not null default 0,
    losses        int          not null default 0,
    picks_against int          not null default 0,
    bans_against  int          not null default 0,

    PRIMARY KEY (god_name, team_name)
);