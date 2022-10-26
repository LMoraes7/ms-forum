package br.com.forum.domain.repository;

import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.mapper.UserFindByIdRowMapper;
import br.com.forum.domain.repository.mapper.UserRowMapper;
import br.com.forum.domain.repository.mapper.response.UserFindById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static br.com.forum.domain.repository.sql.UserSqlCommands.*;
import static br.com.forum.utils.TestUtils.createUser;
import static br.com.forum.utils.TestUtils.createUserFindById;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class UserRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final UserRowMapper userRowMapper = Mockito.mock(UserRowMapper.class);
    private final UserFindByIdRowMapper userFindByIdRowMapper = Mockito.mock(UserFindByIdRowMapper.class);

    private final UserRepository repository = new UserRepository(jdbcTemplate, userRowMapper, userFindByIdRowMapper);

    private User user;
    private UserFindById userFindById;

    @BeforeEach
    void setUp() {
        user = createUser("email");

        userFindById = createUserFindById(user);
    }

    @Test
    void when_receive_a_user_must_save_successfully() {
        when(jdbcTemplate.update(INSERT.sql, user.id(), user.name(), user.email(), user.password(), user.profilePicture())).thenReturn(1);

        repository.save(user);

        verify(jdbcTemplate, only()).update(INSERT.sql, user.id(), user.name(), user.email(), user.password(), user.profilePicture());
        verifyNoInteractions(userRowMapper);
        verifyNoInteractions(userFindByIdRowMapper);
    }

    @Test
    void when_receive_an_id_must_search_o_user_successfully() {
        when(jdbcTemplate.queryForObject(FIND_BY_ID.sql, userFindByIdRowMapper, userFindById.id())).thenReturn(userFindById);

        final var returnUser = repository.findById(userFindById.id());

        assertEquals(userFindById.id(), returnUser.id());

        verify(jdbcTemplate, only()).queryForObject(FIND_BY_ID.sql, userFindByIdRowMapper, userFindById.id());
        verifyNoInteractions(userRowMapper);
        verifyNoInteractions(userFindByIdRowMapper);
    }

    @Test
    void when_receive_an_email_must_search_o_user_successfully() {
        when(jdbcTemplate.queryForObject(FIND_BY_EMAIL.sql, userRowMapper, user.email())).thenReturn(user);

        final var returnUser = repository.findByEmail(user.email());

        assertEquals(user.email(), returnUser.email());

        verify(jdbcTemplate, only()).queryForObject(FIND_BY_EMAIL.sql, userRowMapper, user.email());
        verifyNoInteractions(userRowMapper);
        verifyNoInteractions(userFindByIdRowMapper);
    }

    @Test
    void when_receive_a_profile_picture_must_update_successfully() {
        when(jdbcTemplate.update(UPDATE_PROFILE_PICTURE.sql, user.profilePicture(), user.id())).thenReturn(1);

        repository.updateProfilePicture(user.id(), user.profilePicture());

        verify(jdbcTemplate, only()).update(UPDATE_PROFILE_PICTURE.sql, user.profilePicture(), user.id());
        verifyNoInteractions(userRowMapper);
        verifyNoInteractions(userFindByIdRowMapper);
    }

    @Test
    void when_receive_a_password_must_update_successfully() {
        when(jdbcTemplate.update(UPDATE_PASSWORD.sql, user.password(), user.id())).thenReturn(1);

        repository.updatePassword(user.id(), user.password());

        verify(jdbcTemplate, only()).update(UPDATE_PASSWORD.sql, user.password(), user.id());
        verifyNoInteractions(userRowMapper);
        verifyNoInteractions(userFindByIdRowMapper);
    }
}