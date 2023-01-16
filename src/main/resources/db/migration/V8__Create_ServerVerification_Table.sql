CREATE TABLE IF NOT EXISTS server_verification(
    discord_id bigint not null,
    verified_in bigint not null,

    PRIMARY KEY (discord_id,verified_in),
    FOREIGN KEY (discord_id) REFERENCES verified_player(discord_id),
    FOREIGN KEY (verified_in) REFERENCES league(guild_id)
)