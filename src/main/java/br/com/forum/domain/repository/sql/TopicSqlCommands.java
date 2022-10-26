package br.com.forum.domain.repository.sql;

public enum TopicSqlCommands {

    FIND_BY_ID_WITH_USER("select t.topic_id, t.topic_title, t.topic_contents, t.topic_creation_date, t.topic_update_date, u.user_name, u.user_profile_picture from topics t inner join users u on t.topic_user_id = u.user_id where t.topic_id = ?"),
    FIND_BY_ID("select t.topic_id from topics t where t.topic_id = ?"),
    INSERT("insert into topics (topic_id, topic_title, topic_contents, topic_creation_date, topic_update_date, topic_user_id) values (?, ?, ?, ?, ?, ?)"),
    COUNT("select count(*) from topics");

    public final String sql;

    TopicSqlCommands(String sql) {
        this.sql = sql;
    }

}
