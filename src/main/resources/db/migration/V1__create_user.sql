create table users (
    user_id varchar(12) not null primary key,
    user_name varchar(255) not null,
    user_email varchar(255) not null unique,
    user_password varchar(255) not null,
    user_profile_picture TEXT null
);