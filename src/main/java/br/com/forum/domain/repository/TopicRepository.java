package br.com.forum.domain.repository;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.repository.mapper.TopicFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.TopicFindByIdWithUserRowMapper;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindByIdWithUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static br.com.forum.domain.repository.sql.TopicSqlCommands.*;

@Repository
public class TopicRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TopicFindByIdWithUserRowMapper topicFindByIdWithUserRowMapper;
    private final TopicFindByIdRowMapper topicFindByIdRowMapper;

    public TopicRepository(final JdbcTemplate jdbcTemplate, final TopicFindByIdWithUserRowMapper topicFindByIdWithUserRowMapper, final TopicFindByIdRowMapper topicFindByIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.topicFindByIdWithUserRowMapper = topicFindByIdWithUserRowMapper;
        this.topicFindByIdRowMapper = topicFindByIdRowMapper;
    }

    public void save(final Topic topic) {
        jdbcTemplate.update(INSERT.sql, topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), topic.user().id());
    }

    public TopicFindByIdWithUser findByIdWithUser(final String id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_WITH_USER.sql, topicFindByIdWithUserRowMapper, id);
    }

    public TopicFindById findById(final String id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID.sql, topicFindByIdRowMapper, id);
    }
}
