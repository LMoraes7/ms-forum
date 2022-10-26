package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.repository.mapper.response.TopicFindById;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class TopicFindByIdRowMapper implements RowMapper<TopicFindById> {


    @Override
    public TopicFindById mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TopicFindById(
                rs.getString("topic_id"),
                rs.getString("topic_title"),
                rs.getString("topic_contents"),
                rs.getObject("topic_creation_date", LocalDateTime.class),
                rs.getObject("topic_update_date", LocalDateTime.class),
                new TopicFindById.User(
                        rs.getString("user_name"),
                        rs.getString("user_profile_picture")
                )
        );
    }
}
