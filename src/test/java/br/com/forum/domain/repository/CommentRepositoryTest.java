package br.com.forum.domain.repository;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.repository.mapper.CommentFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.response.CommentFindById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static br.com.forum.domain.repository.sql.CommentSqlCommands.FIND_BY_TOPIC_ID_WITH_USER;
import static br.com.forum.domain.repository.sql.CommentSqlCommands.INSERT;
import static br.com.forum.utils.TestUtils.*;
import static org.mockito.Mockito.*;

final class CommentRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final CommentFindByIdRowMapper commentFindByIdRowMapper = Mockito.mock(CommentFindByIdRowMapper.class);
    private final CommentRepository repository = new CommentRepository(jdbcTemplate, commentFindByIdRowMapper);

    private Comment comment;
    private CommentFindById commentFindById;

    @BeforeEach
    void setUp() {
        var user = createUser("email");
        comment = createComment(createTopic(user, null), user);

        commentFindById = createCommentFindById(comment);
    }

    @Test
    void when_receive_a_comment_must_save_successfully() {
        when(jdbcTemplate.update(INSERT.sql, comment.id(), comment.contents(), comment.creationDate(), comment.updateDate(), comment.topic().id(), comment.user().id())).thenReturn(1);

        repository.save(comment);

        verify(jdbcTemplate, only()).update(INSERT.sql, comment.id(), comment.contents(), comment.creationDate(), comment.updateDate(), comment.topic().id(), comment.user().id());
        verifyNoInteractions(commentFindByIdRowMapper);
    }

    @Test
    void when_receive_a_topic_id_must_search_the_list_of_comments_successfully() {
        when(jdbcTemplate.query(FIND_BY_TOPIC_ID_WITH_USER.sql, commentFindByIdRowMapper, comment.topic().id())).thenReturn(List.of(commentFindById));

        repository.findByIdWithUser(comment.topic().id());

        verify(jdbcTemplate, only()).query(FIND_BY_TOPIC_ID_WITH_USER.sql, commentFindByIdRowMapper, comment.topic().id());
        verifyNoInteractions(commentFindByIdRowMapper);
    }
}