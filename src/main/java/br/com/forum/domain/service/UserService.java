package br.com.forum.domain.service;

import br.com.forum.domain.exception.BusinessException;
import br.com.forum.domain.exception.NotFoundException;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.UserRepository;
import br.com.forum.domain.repository.mapper.response.UserFindById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public final class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;
    private final GeneratorIdentifierService generatorIdentifierService;

    public UserService(final UserRepository userRepository, final PasswordEncoderService passwordEncoderService, final GeneratorIdentifierService generatorIdentifierService) {
        this.userRepository = userRepository;
        this.passwordEncoderService = passwordEncoderService;
        this.generatorIdentifierService = generatorIdentifierService;
    }

    public User save(final String name, final String email, final String password, final String profilePicture) {
        final var userId = generatorIdentifierService.userId();
        logger.info("starting user persistence process of id {} and email {}", userId, email);
        final var user = new User(userId, name, email, passwordEncoderService.encrypt(password), profilePicture);

        try {
            this.userRepository.save(user);
            logger.info("finalizing user persistence process of id {} and email {}", userId, email);
            return user;
        } catch (DuplicateKeyException ex) {
            logger.info("it was not possible to register the user with id {} and email {}", user.id(), user.email());
            logger.info("email provided has already been registered");
            throw new BusinessException("email provided has already been registered");
        }
    }

    public UserFindById findById(final String id) {
        try {
            logger.info("starting user search process by id {}", id);
            final var user = this.userRepository.findById(id);
            logger.info("finalizing user search process by id {}", id);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            logger.info("user id {} does not exist", id);
            throw new NotFoundException(User.class, id);
        }
    }

    public User findByEmail(final String email) {
        try {
            logger.info("starting user search process by email {}", email);
            final var user = this.userRepository.findByEmail(email);
            logger.info("finalizing user search process by email {}", email);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            logger.info("user email {} does not exist", email);
            throw new NotFoundException(User.class, email);
        }
    }

    public void updateProfilePicture(final String id, final String profilePicture) {
        logger.info("starting profile picture update process for user of id {}", id);
        this.userRepository.updateProfilePicture(id, profilePicture);
        logger.info("finalizing profile picture update process for user of id {}", id);
    }

    public void updatePassword(final String id, final String password) {
        logger.info("starting password update process for user of id {}", id);
        final var encrypt = this.passwordEncoderService.encrypt(password);
        this.userRepository.updatePassword(id, encrypt);
        logger.info("finalizing password update process for user id {}", id);
    }
}
