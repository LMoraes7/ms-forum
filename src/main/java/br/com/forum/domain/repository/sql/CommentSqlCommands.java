package br.com.forum.domain.repository.sql;

public enum CommentSqlCommands {

    INSERT("insert into comments (comment_id, comment_contents, comment_creation_date, comment_update_date, comment_topic_id, comment_user_id) values (?, ?, ?, ?, ?, ?)"),
    FIND_BY_TOPIC_ID_WITH_USER("select c.comment_id, c.comment_contents, c.comment_creation_date, c.comment_update_date, u.user_name, u.user_profile_picture from comments c inner join users u on c.comment_user_id = u.user_id where c.comment_topic_id = ?"),
    COUNT("select count(*) from comments");

    public final String sql;

    CommentSqlCommands(String sql) {
        this.sql = sql;
    }

}
