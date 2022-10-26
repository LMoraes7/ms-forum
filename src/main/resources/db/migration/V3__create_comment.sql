create table comments (
    comment_id varchar(12) not null primary key,
    comment_contents varchar(255) not null,
    comment_creation_date TIMESTAMP not null,
    comment_update_date TIMESTAMP not null,
    comment_topic_id varchar(12) not null,
    foreign key (comment_topic_id) references topics(topic_id),
    comment_user_id varchar(12) not null,
    foreign key (comment_user_id) references users(user_id)
);