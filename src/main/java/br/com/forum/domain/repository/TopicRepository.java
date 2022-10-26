package br.com.forum.domain.repository;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.repository.mapper.TopicFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static br.com.forum.domain.repository.sql.TopicSqlCommands.FIND_BY_ID_WITH_USER;
import static br.com.forum.domain.repository.sql.TopicSqlCommands.INSERT;

@Repository
public class TopicRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TopicFindByIdRowMapper topicFindByIdRowMapper;

    public TopicRepository(final JdbcTemplate jdbcTemplate, final TopicFindByIdRowMapper topicFindByIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.topicFindByIdRowMapper = topicFindByIdRowMapper;
    }

    public void save(final Topic topic) {
        jdbcTemplate.update(INSERT.sql, topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), topic.user().id());
    }

    public TopicFindById findByIdWithUser(final String id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_WITH_USER.sql, topicFindByIdRowMapper, id);
    }

}
