package br.com.forum.domain.repository;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.repository.mapper.TopicFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.TopicFindByIdWithUserRowMapper;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindByIdWithUser;
import br.com.forum.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static br.com.forum.domain.repository.sql.TopicSqlCommands.*;
import static br.com.forum.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class TopicRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final TopicFindByIdWithUserRowMapper topicFindByIdWithUserRowMapper = mock(TopicFindByIdWithUserRowMapper.class);
    private final TopicFindByIdRowMapper topicFindByIdRowMapper = mock(TopicFindByIdRowMapper.class);

    private final TopicRepository repository = new TopicRepository(jdbcTemplate, topicFindByIdWithUserRowMapper, topicFindByIdRowMapper);

    private Topic topic;
    private TopicFindByIdWithUser topicFindByIdWithUser;
    private TopicFindById topicFindById;

    @BeforeEach
    void setUp() {
        topic = createTopic(TestUtils.createUser("email"), null);

        topicFindByIdWithUser = createTopicFindByIdWithUser(topic);

        topicFindById = createTopicFindById(topic);
    }

    @Test
    void when_receive_a_topic_must_save_successfully() {
        when(jdbcTemplate.update(INSERT.sql, topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), topic.user().id())).thenReturn(1);

        repository.save(topic);

        verify(jdbcTemplate, only()).update(INSERT.sql, topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), topic.user().id());
        verifyNoInteractions(topicFindByIdWithUserRowMapper, topicFindByIdRowMapper);
    }

    @Test
    void when_receive_a_id_must_search_the_topic_with_user_successfully() {
        when(jdbcTemplate.queryForObject(FIND_BY_ID_WITH_USER.sql, topicFindByIdWithUserRowMapper, topicFindByIdWithUser.id())).thenReturn(topicFindByIdWithUser);

        final var returnTopic = repository.findByIdWithUser(topicFindByIdWithUser.id());

        assertEquals(returnTopic, topicFindByIdWithUser);

        verify(jdbcTemplate, only()).queryForObject(FIND_BY_ID_WITH_USER.sql, topicFindByIdWithUserRowMapper, topic.id());
        verifyNoInteractions(topicFindByIdWithUserRowMapper, topicFindByIdRowMapper);
    }

    @Test
    void when_receive_a_id_must_search_the_topic_successfully() {
        when(jdbcTemplate.queryForObject(FIND_BY_ID.sql, topicFindByIdRowMapper, topicFindById.id())).thenReturn(topicFindById);

        final var returnTopic = repository.findById(topicFindById.id());

        assertEquals(returnTopic, topicFindById);

        verify(jdbcTemplate, only()).queryForObject(FIND_BY_ID.sql, topicFindByIdRowMapper, topicFindById.id());
        verifyNoInteractions(topicFindByIdWithUserRowMapper, topicFindByIdRowMapper);
    }
}