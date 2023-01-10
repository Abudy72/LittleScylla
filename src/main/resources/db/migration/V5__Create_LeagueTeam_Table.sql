CREATE TABLE league_team(
    team_name varchar(70) not null,
    captain varchar(70) not null,
    coach varchar(70) not null,
    sub varchar(70) not null,
    division_name varchar(70) not null,

    PRIMARY KEY (team_name,division_name),
    FOREIGN KEY (division_name) REFERENCES division(division_name)
);