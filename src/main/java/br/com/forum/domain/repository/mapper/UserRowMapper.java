package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("user_id"),
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getString("user_password"),
                rs.getString("user_profile_picture")
        );
    }
}
