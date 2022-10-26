package br.com.forum.domain.repository;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static br.com.forum.domain.repository.sql.CommentSqlCommands.COUNT;
import static br.com.forum.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Tag("integration")
final class CommentRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User user;
    private Topic topic;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = createUser("email");
        topic = createTopic(user, null);
        comment = createComment(topic, user);
    }

    @Test
    @Transactional
    void when_receive_a_comment_must_save_successfully() {
        userRepository.save(user);
        topicRepository.save(topic);

        assertEquals(queryCount(), 0);

        commentRepository.save(comment);

        assertEquals(queryCount(), 1);
    }

    @Test
    @Transactional
    void when_receive_a_topic_id_must_search_the_list_of_comments_successfully() {
        userRepository.save(user);
        topicRepository.save(topic);
        commentRepository.save(comment);

        var comments = commentRepository.findByIdWithUser(comment.topic().id());

        assertFalse(comments.isEmpty());
        assertEquals(comments.size(), 1);
        assertEquals(comments.stream().findFirst().get().id(), comment.id());
    }

    private Integer queryCount() {
        return jdbcTemplate.queryForObject(COUNT.sql, Integer.class);
    }
}