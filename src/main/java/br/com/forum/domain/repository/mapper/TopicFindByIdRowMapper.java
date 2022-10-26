package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.repository.mapper.response.TopicFindById;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TopicFindByIdRowMapper implements RowMapper<TopicFindById> {

    @Override
    public TopicFindById mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TopicFindById(rs.getString("topic_id"));
    }
}
