package br.com.forum.domain.repository.sql;

public enum UserSqlCommands {

    FIND_BY_ID("select u.user_id, u.user_name, u.user_email, u.user_profile_picture from users u where u.user_id = ?"),
    FIND_BY_EMAIL("select * from users u where u.user_email = ?"),
    INSERT("insert into users (user_id, user_name, user_email, user_password, user_profile_picture) values (?, ?, ?, ?, ?)"),
    UPDATE_PROFILE_PICTURE("update users u set u.user_profile_picture = ? where u.user_id = ?"),
    UPDATE_PASSWORD("update users u set u.user_password = ? where u.user_id = ?"),
    COUNT("select count(*) from users");

    public final String sql;

    UserSqlCommands(String sql) {
        this.sql = sql;
    }

}
