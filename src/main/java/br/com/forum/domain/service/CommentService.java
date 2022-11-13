package br.com.forum.domain.service;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.CommentRepository;
import br.com.forum.web.controller.request.CommentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public final class CommentService {

    private static final String classNameLogger = "[" + CommentService.class.getSimpleName() + "] ";
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final TopicService topicService;
    private final GeneratorIdentifierService generatorIdentifierService;

    public CommentService(
            final CommentRepository commentRepository,
            final TopicService topicService,
            final GeneratorIdentifierService generatorIdentifierService
    ) {
        this.commentRepository = commentRepository;
        this.topicService = topicService;
        this.generatorIdentifierService = generatorIdentifierService;
    }

    public Comment save(final User user, final String topicId, final CommentRequest commentRequest) {
        final var comment = commentRequest.toDomain(user, generatorIdentifierService.commentId(), topicService.findById(topicId));
        logger.info(classNameLogger + "starting the process of registering a comment of id {} for the topic of id {}", comment.id(), comment.topic().id());
        commentRepository.save(comment);
        logger.info(classNameLogger + "finalizing the process of registering a comment of id {} for the topic of id {}", comment.id(), comment.topic().id());
        return comment;
    }
}
