create table users (
    id varchar(12) not null primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    profile_picture TEXT null
);