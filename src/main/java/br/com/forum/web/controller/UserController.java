package br.com.forum.web.controller;

import br.com.forum.domain.model.User;
import br.com.forum.domain.service.UserService;
import br.com.forum.security.service.TokenService;
import br.com.forum.web.controller.request.LoginRequest;
import br.com.forum.web.controller.request.UserRequest;
import br.com.forum.web.controller.request.UserUpdatePasswordRequest;
import br.com.forum.web.controller.request.UserUpdateRequest;
import br.com.forum.web.controller.response.LoginResponse;
import br.com.forum.web.controller.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private static final String classNameLogger = "[" + UserController.class.getSimpleName() + "] ";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UserService userService;

    public UserController(
            final AuthenticationManager authManager,
            final TokenService tokenService,
            final UserService userService
    ) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request) {
        logger.info(classNameLogger + "receiving request to log in user with email {}", request.getEmail());
        final var user = (User) authManager.authenticate(request.toAuthentication()).getPrincipal();
        final var token = tokenService.generateToken(user);
        logger.info(classNameLogger + "finalizing request to log in user with email {}", request.getEmail());
        return ResponseEntity.ok(new LoginResponse("Bearer", token, user.name(), user.profilePicture()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid final UserRequest request) {
        logger.info(classNameLogger + "receiving request to register user with email {}", request.getEmail());
        final var user = userService.save(request.getName(), request.getEmail(), request.getPassword(), request.getImage());
        logger.info(classNameLogger + "finalizing request to register user with email {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user.id(), user.name(), user.email(), user.profilePicture()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable final String id) {
        logger.info(classNameLogger + "receiving request to perform fetch user of id {}", id);
        final var user = userService.findById(id);
        logger.info(classNameLogger + "finalizing request to perform fetch user of id {}", id);
        return ResponseEntity.ok(UserResponse.toResponse(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(
            @PathVariable final String id,
            @RequestBody @Valid final UserUpdateRequest request
    ) {
        logger.info(classNameLogger + "receiving request to update user's profile picture from id {}", id);
        userService.updateProfilePicture(id, request.getImage());
        logger.info(classNameLogger + "finalizing request to update user's profile picture from id {}", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<Object> updatePassword(
            @PathVariable final String id,
            @RequestBody @Valid final UserUpdatePasswordRequest requestPassword
    ) {
        logger.info(classNameLogger + "receiving request to update user's password from id {}", id);
        userService.updatePassword(id, requestPassword.getPassword());
        logger.info(classNameLogger + "finalizing request to update user's password from id {}", id);
        return ResponseEntity.noContent().build();
    }
}
