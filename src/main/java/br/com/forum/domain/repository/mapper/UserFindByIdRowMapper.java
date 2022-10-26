package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.mapper.response.UserFindById;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserFindByIdRowMapper implements RowMapper<UserFindById> {

    @Override
    public UserFindById mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserFindById(
                rs.getString("user_id"),
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getString("user_profile_picture")
        );
    }
}
