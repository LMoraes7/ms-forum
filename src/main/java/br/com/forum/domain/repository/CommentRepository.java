package br.com.forum.domain.repository;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.repository.mapper.CommentFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.response.CommentFindById;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static br.com.forum.domain.repository.sql.CommentSqlCommands.FIND_BY_TOPIC_ID_WITH_USER;
import static br.com.forum.domain.repository.sql.CommentSqlCommands.INSERT;

@Repository
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CommentFindByIdRowMapper commentFindByIdRowMapper;

    public CommentRepository(final JdbcTemplate jdbcTemplate, final CommentFindByIdRowMapper commentFindByIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentFindByIdRowMapper = commentFindByIdRowMapper;
    }

    public void save(final Comment comment) {
        jdbcTemplate.update(INSERT.sql, comment.id(), comment.contents(), comment.creationDate(), comment.updateDate(), comment.topic().id(), comment.user().id());
    }

    public List<CommentFindById> findByIdWithUser(final String topicId) {
        return jdbcTemplate.query(FIND_BY_TOPIC_ID_WITH_USER.sql, commentFindByIdRowMapper, topicId);
    }

}
