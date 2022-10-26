create table topics (
    topic_id varchar(12) not null primary key,
    topic_title varchar(255) not null,
    topic_contents TEXT not null,
    topic_creation_date TIMESTAMP not null,
    topic_update_date TIMESTAMP not null,
    topic_user_id varchar(12) not null,
    foreign key (topic_user_id) references users(user_id)
);