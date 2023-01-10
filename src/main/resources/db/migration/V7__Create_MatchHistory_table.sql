CREATE TABLE match_history(
    matchId bigint PRIMARY KEY not null,
    saved_by bigint not null,
    date_saved Date not null,
    publicDate Date not null,
    division varchar(70) not null,

    FOREIGN KEY (division) REFERENCES division(division_name)
)