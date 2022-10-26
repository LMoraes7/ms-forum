package br.com.forum.domain.repository.mapper;

import br.com.forum.domain.repository.mapper.response.CommentFindById;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class CommentFindByIdRowMapper implements RowMapper<CommentFindById> {

    @Override
    public CommentFindById mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommentFindById(
                rs.getString("comment_id"),
                rs.getString("comment_contents"),
                rs.getObject("comment_creation_date", LocalDateTime.class),
                rs.getObject("comment_update_date", LocalDateTime.class),
                new CommentFindById.User(
                        rs.getString("user_name"),
                        rs.getString("user_profile_picture")
                )
        );
    }
}
