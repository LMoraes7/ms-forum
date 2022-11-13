package br.com.forum.web.controller;

import br.com.forum.domain.model.User;
import br.com.forum.domain.service.CommentService;
import br.com.forum.domain.service.TopicService;
import br.com.forum.web.controller.request.CommentRequest;
import br.com.forum.web.controller.request.TopicRequest;
import br.com.forum.web.controller.response.TopicResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/topic")
@Validated
public class TopicController {

    private static final String classNameLogger = "[" + TopicController.class.getSimpleName() + "] ";
    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    private final TopicService topicService;
    private final CommentService commentService;

    public TopicController(final TopicService topicService, final CommentService commentService) {
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestBody @Valid final TopicRequest topicRequest,
            final Authentication authentication
    ) {
        final var user = getUser(authentication);
        logger.info(classNameLogger + "receiving request to create a topic for user of id {}", user.id());
        topicService.save(user, topicRequest);
        logger.info(classNameLogger + "finalizing request to create a topic for user of id {}", user.id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> createComment(
            @PathVariable("id") final String topicId,
            @RequestBody @Valid final CommentRequest commentRequest,
            final Authentication authentication
    ) {
        final var user = getUser(authentication);
        logger.info(classNameLogger + "receiving request to create a comment for topic of id {} made by user of id {}", topicId, user.id());
        commentService.save(user, topicId, commentRequest);
        logger.info(classNameLogger + "finalizing request to create a comment for topic of id {} made by user of id {}", topicId, user.id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> findById(@PathVariable final String id) {
        logger.info(classNameLogger + "receiving request for a topic of id {}", id);
        final var topicWithComments = topicService.findByIdWithUserAndComments(id);
        logger.info(classNameLogger + "finalizing request for a topic of id {}", id);
        return ResponseEntity.ok(TopicResponse.toResponse(topicWithComments));
    }

    private User getUser(final Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

}
