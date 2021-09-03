create database mypages;
use mypages;
create table if not exists user
(
    id           bigint primary key auto_increment not null,
    created_time datetime                          not null,
    updated_time datetime                          null,
    username     varchar(20)                       not null,
    password     varchar(40)                       not null,
    nickname     varchar(20)                       not null,
    status       tinyint default 1                 not null
);
create unique index uk_user_username on user (username);
create index idx_user_nickname on user (nickname);