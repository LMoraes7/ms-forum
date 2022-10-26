package br.com.forum.domain.repository;

import br.com.forum.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static br.com.forum.domain.repository.sql.UserSqlCommands.COUNT;
import static br.com.forum.utils.TestUtils.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Tag("integration")
final class UserRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository repository;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser("email");
    }

    @Test
    @Transactional
    void when_receive_a_user_must_save_successfully() {
        assertEquals(queryCount(), 0);

        repository.save(user);

        assertEquals(queryCount(), 1);
    }

    @Test
    @Transactional
    void when_receive_an_id_must_search_o_user_successfully() {
        assertEquals(queryCount(), 0);

        repository.save(user);

        assertEquals(queryCount(), 1);

        final var returnUser = repository.findById(user.id());

        assertEquals(user.id(), returnUser.id());
        assertEquals(user.name(), returnUser.name());
        assertEquals(user.email(), returnUser.email());
        assertEquals(user.profilePicture(), returnUser.profilePicture());
    }

    @Test
    @Transactional
    void when_receive_an_email_must_search_o_user_successfully() {
        assertEquals(queryCount(), 0);

        repository.save(user);

        assertEquals(queryCount(), 1);

        final var returnUser = repository.findByEmail(user.email());

        assertEquals(user, returnUser);
    }

    @Test
    @Transactional
    void when_receive_a_profile_picture_must_update_successfully() {
        repository.save(user);

        final var newProfilePicture = "newProfilePicture";
        repository.updateProfilePicture(user.id(), newProfilePicture);

        final var returnUser = repository.findById(user.id());

        assertEquals(returnUser.profilePicture(), newProfilePicture);
    }

    @Test
    @Transactional
    void when_receive_a_password_must_update_successfully() {
        repository.save(user);

        final var newPassword = "newPassword";
        repository.updatePassword(user.id(), newPassword);

        final var returnUser = repository.findByEmail(user.email());

        assertEquals(returnUser.password(), newPassword);
    }

    private Integer queryCount() {
        return jdbcTemplate.queryForObject(COUNT.sql, Integer.class);
    }
}