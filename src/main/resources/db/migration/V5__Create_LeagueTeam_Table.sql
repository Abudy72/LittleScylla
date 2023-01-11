CREATE TABLE league_team(
    team_name varchar(70) not null,
    captain bigint not null,
    team_member1 bigint,
    team_member2 bigint,
    team_member3 bigint,
    team_member4 bigint,
    coach bigint,
    sub bigint,
    division_name varchar(70) not null,

    PRIMARY KEY (team_name,division_name),
    FOREIGN KEY (division_name) REFERENCES division(division_name)
);