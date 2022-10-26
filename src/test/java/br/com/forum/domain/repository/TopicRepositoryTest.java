package br.com.forum.domain.repository;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.repository.mapper.TopicFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static br.com.forum.domain.repository.sql.TopicSqlCommands.FIND_BY_ID_WITH_USER;
import static br.com.forum.domain.repository.sql.TopicSqlCommands.INSERT;
import static br.com.forum.utils.TestUtils.createTopic;
import static br.com.forum.utils.TestUtils.createTopicFindById;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class TopicRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final TopicFindByIdRowMapper topicFindByIdRowMapper = Mockito.mock(TopicFindByIdRowMapper.class);

    private final TopicRepository repository = new TopicRepository(jdbcTemplate, topicFindByIdRowMapper);

    private Topic topic;
    private TopicFindById topicFindById;

    @BeforeEach
    void setUp() {
        topic = createTopic(TestUtils.createUser("email"), null);

        topicFindById = createTopicFindById(topic);
    }

    @Test
    void when_receive_a_topic_must_save_successfully() {
        when(jdbcTemplate.update(INSERT.sql, topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), topic.user().id())).thenReturn(1);

        repository.save(topic);

        verify(jdbcTemplate, only()).update(INSERT.sql, topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), topic.user().id());
        verifyNoInteractions(topicFindByIdRowMapper);
    }

    @Test
    void when_receive_a_id_must_search_the_topic_successfully() {
        when(jdbcTemplate.queryForObject(FIND_BY_ID_WITH_USER.sql, topicFindByIdRowMapper, topicFindById.id())).thenReturn(topicFindById);

        final var returnTopic = repository.findByIdWithUser(topicFindById.id());

        assertEquals(returnTopic, topicFindById);

        verify(jdbcTemplate, only()).queryForObject(FIND_BY_ID_WITH_USER.sql, topicFindByIdRowMapper, topic.id());
        verifyNoInteractions(topicFindByIdRowMapper);
    }
}