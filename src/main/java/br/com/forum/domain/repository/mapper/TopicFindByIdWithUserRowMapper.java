package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.repository.mapper.response.TopicFindByIdWithUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class TopicFindByIdWithUserRowMapper implements RowMapper<TopicFindByIdWithUser> {

    @Override
    public TopicFindByIdWithUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TopicFindByIdWithUser(
                rs.getString("topic_id"),
                rs.getString("topic_title"),
                rs.getString("topic_contents"),
                rs.getObject("topic_creation_date", LocalDateTime.class),
                rs.getObject("topic_update_date", LocalDateTime.class),
                new TopicFindByIdWithUser.User(
                        rs.getString("user_name"),
                        rs.getString("user_profile_picture")
                )
        );
    }
}
