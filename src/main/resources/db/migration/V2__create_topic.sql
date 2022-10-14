create table topics (
    id varchar(12) not null primary key,
    title varchar(255) not null,
    contents TEXT not null,
    creation_date TIMESTAMP not null,
    update_date TIMESTAMP not null,
    user_id varchar(12) not null,
    foreign key (user_id) references users(id)
);