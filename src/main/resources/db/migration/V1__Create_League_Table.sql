CREATE TABLE League(
    guild_id bigint NOT NULL PRIMARY KEY,
    guild_name varchar(255) not null UNIQUE,
    bronze_uid bigint not null,
    silver_uid bigint not null,
    gold_uid bigint not null,
    platinum_uid bigint not null,
    diamond_uid bigint not null,
    masters_uid bigint not null,
    grandmaster_uid bigint not null,
    verification_Channel bigint not null,
    staff_role bigint not null,
    verified_role bigint not null,
    not_verified_role bigint not null,
    pc_uid bigint not null,
    Xbox_uid bigint not null,
    psn_uid bigint not null
);