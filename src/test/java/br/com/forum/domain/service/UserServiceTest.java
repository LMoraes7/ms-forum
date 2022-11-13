package br.com.forum.domain.service;

import br.com.forum.domain.exception.BusinessException;
import br.com.forum.domain.exception.NotFoundException;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.UserRepository;
import br.com.forum.domain.repository.mapper.response.UserFindById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import static br.com.forum.utils.TestUtils.createUser;
import static br.com.forum.utils.TestUtils.createUserFindById;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

final class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoderService passwordEncoderService = Mockito.mock(PasswordEncoderService.class);
    private final GeneratorIdentifierService generatorIdentifierService = Mockito.mock(GeneratorIdentifierService.class);

    private final UserService service = new UserService(userRepository, passwordEncoderService, generatorIdentifierService);

    private User user;
    private UserFindById userFindById;

    @BeforeEach
    void setUp() {
        user = createUser("email");

        userFindById = createUserFindById(user);
    }

    @Test
    void when_receive_a_request_to_save_a_user_must_save_successfully() {
        when(generatorIdentifierService.userId()).thenReturn(user.id());
        when(passwordEncoderService.encrypt(user.password())).thenReturn(user.password());

        final var returnUser = service.save(user.name(), user.email(), user.password(), user.profilePicture());

        assertEquals(returnUser.name(), user.name());
        assertEquals(returnUser.email(), user.email());
        assertEquals(returnUser.profilePicture(), user.profilePicture());

        verify(generatorIdentifierService, only()).userId();
        verify(passwordEncoderService, only()).encrypt(user.password());
        verify(userRepository, only()).save(user);
    }

    @Test
    void when_receiving_a_request_to_save_a_user_and_receiving_a_DuplicateKeyException_should_throw_a_BusinessException() {
        when(generatorIdentifierService.userId()).thenReturn(user.id());
        when(passwordEncoderService.encrypt(user.password())).thenReturn(user.password());
        doThrow(DuplicateKeyException.class).when(userRepository).save(user);

        assertThrows(BusinessException.class, () -> service.save(user.name(), user.email(), user.password(), user.profilePicture()));

        verify(generatorIdentifierService, only()).userId();
        verify(passwordEncoderService, only()).encrypt(user.password());
        verify(userRepository, only()).save(user);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_user_by_id_it_should_run_successfully() {
        when(userRepository.findById(userFindById.id())).thenReturn(userFindById);

        final var returnUser = service.findById(userFindById.id());

        assertEquals(returnUser.id(), userFindById.id());

        verify(userRepository, only()).findById(userFindById.id());
        verifyNoInteractions(generatorIdentifierService, passwordEncoderService);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_user_by_id_should_throw_a_NotFoundException_when_receiving_an_EmptyResultDataAccessException() {
        when(userRepository.findById(userFindById.id())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NotFoundException.class, () -> service.findById(userFindById.id()));

        verify(userRepository, only()).findById(userFindById.id());
        verifyNoInteractions(generatorIdentifierService, passwordEncoderService);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_user_by_email_it_should_run_successfully() {
        when(userRepository.findByEmail(user.email())).thenReturn(user);

        final var returnUser = service.findByEmail(user.email());

        assertEquals(returnUser.email(), user.email());

        verify(userRepository, only()).findByEmail(user.email());
        verifyNoInteractions(generatorIdentifierService, passwordEncoderService);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_user_by_email_should_throw_a_NotFoundException_when_receiving_an_EmptyResultDataAccessException() {
        when(userRepository.findByEmail(user.email())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NotFoundException.class, () -> service.findByEmail(user.email()));

        verify(userRepository, only()).findByEmail(user.email());
        verifyNoInteractions(generatorIdentifierService, passwordEncoderService);
    }

    @Test
    void when_you_receive_a_request_to_update_a_user_photo_it_should_update_successfully() {
        when(userRepository.findById(user.id())).thenReturn(userFindById);

        service.updateProfilePicture(user.id(), user.profilePicture());

        verify(userRepository, times(1)).findById(user.id());
        verify(userRepository, times(1)).updateProfilePicture(user.id(), user.profilePicture());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoderService, generatorIdentifierService);
    }

    @Test
    void when_you_receive_a_request_to_update_a_user_password_it_should_update_successfully() {
        when(userRepository.findById(user.id())).thenReturn(userFindById);
        when(passwordEncoderService.encrypt(user.password())).thenReturn(user.password());

        service.updatePassword(user.id(), user.password());

        verify(passwordEncoderService, only()).encrypt(user.password());
        verify(userRepository, times(1)).findById(user.id());
        verify(userRepository, times(1)).updatePassword(user.id(), user.password());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(generatorIdentifierService);
    }
}