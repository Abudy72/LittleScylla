CREATE TABLE league_player_stats(
        ign varchar(70) not null,
        guild_id bigint NOT NULL,
        damage_taken int NOT NULL default 0,
        damage_mitigated int NOT NULL default 0,
        player_damage int NOT NULL default 0,
        losses int NOT NULL default 0,
        wins int NOT NULL default 0,
        kills int NOT NULL default 0,
        deaths int NOT NULL default 0,
        assists int NOT NULL default 0,
        gold_earned int NOT NULL default 0,
        wards_placed int NOT NULL default 0,
        total_match_duration int NOT NULL default 0,
        first_blood_kills int NOT NULL default 0,
        team_name varchar(70),
        division_name varchar(70) NOT NULL,

        PRIMARY KEY (ign,division_name),
        FOREIGN KEY (division_Name) references division(division_name)
);