package br.com.forum.domain.repository;

import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.mapper.UserFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.UserRowMapper;
import br.com.forum.domain.repository.mapper.response.UserFindById;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static br.com.forum.domain.repository.sql.UserSqlCommands.*;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    private final UserFindByIdRowMapper userFindByIdRowMapper;

    public UserRepository(final JdbcTemplate jdbcTemplate, final UserRowMapper userRowMapper, final UserFindByIdRowMapper userFindByIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.userFindByIdRowMapper = userFindByIdRowMapper;
    }

    public void save(final User user) {
        this.jdbcTemplate.update(INSERT.sql, user.id(), user.name(), user.email(), user.password(), user.profilePicture());
    }

    public UserFindById findById(final String id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID.sql, userFindByIdRowMapper, id);
    }

    public User findByEmail(final String email) {
        return this.jdbcTemplate.queryForObject(FIND_BY_EMAIL.sql, userRowMapper, email);
    }

    public void updateProfilePicture(final String id, final String profilePicture) {
        this.jdbcTemplate.update(UPDATE_PROFILE_PICTURE.sql, profilePicture, id);
    }

    public void updatePassword(final String id, final String password) {
        this.jdbcTemplate.update(UPDATE_PASSWORD.sql, password, id);
    }
}
