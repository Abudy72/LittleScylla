CREATE TABLE IF NOT EXISTS match_history(
    matchId bigint PRIMARY KEY not null,
    saved_by bigint not null,
    date_saved Date not null DEFAULT CURRENT_DATE,
    publicDate Date,
    division varchar(70) not null,
    status boolean NOT NULL,

    FOREIGN KEY (division) REFERENCES division(division_name)
)