package br.com.forum.domain.repository;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static br.com.forum.domain.repository.sql.TopicSqlCommands.COUNT;
import static br.com.forum.utils.TestUtils.createTopic;
import static br.com.forum.utils.TestUtils.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Tag("integration")
final class TopicRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    private User user;
    private Topic topic;

    @BeforeEach
    void setUp() {
        user = createUser("email");
        topic = createTopic(user, null);
    }

    @Test
    @Transactional
    void when_receive_a_topic_must_save_successfully() {
        userRepository.save(user);

        assertEquals(queryCount(), 0);

        topicRepository.save(topic);

        assertEquals(queryCount(), 1);
    }

    @Test
    @Transactional
    void when_receive_um_id_must_search_the_topic_with_user_successfully() {
        userRepository.save(user);
        topicRepository.save(topic);

        final var returnTopic = topicRepository.findByIdWithUser(topic.id());

        assertEquals(topic.id(), returnTopic.id());
        assertEquals(topic.user().name(), returnTopic.user().name());
    }

    @Test
    @Transactional
    void when_receive_um_id_must_search_the_topic_successfully() {
        userRepository.save(user);
        topicRepository.save(topic);

        final var returnTopic = topicRepository.findById(topic.id());

        assertEquals(topic.id(), returnTopic.id());
    }

    private Integer queryCount() {
        return jdbcTemplate.queryForObject(COUNT.sql, Integer.class);
    }
}