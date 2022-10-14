create table comments (
    id varchar(12) not null primary key,
    contents varchar(255) not null,
    creation_date TIMESTAMP not null,
    update_date TIMESTAMP not null,
    topic_id varchar(12) not null,
    foreign key (topic_id) references topics(id),
    user_id varchar(12) not null,
    foreign key (user_id) references users(id)
);