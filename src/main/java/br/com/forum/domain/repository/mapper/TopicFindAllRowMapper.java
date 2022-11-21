package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.repository.mapper.response.TopicFindAll;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class TopicFindAllRowMapper implements RowMapper<TopicFindAll> {

    @Override
    public TopicFindAll mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TopicFindAll(
                rs.getString("topic_id"),
                rs.getString("topic_title"),
                rs.getObject("topic_creation_date", LocalDateTime.class)
        );
    }
}
