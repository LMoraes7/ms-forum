package br.com.forum.domain.service;

import br.com.forum.domain.exception.NotFoundException;
import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.CommentRepository;
import br.com.forum.domain.repository.TopicRepository;
import br.com.forum.domain.repository.mapper.response.CommentFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindByIdWithUser;
import br.com.forum.web.controller.request.TopicRequest;
import org.flywaydb.core.internal.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class TopicService {

    private static final String classNameLogger = "[" + TopicService.class.getSimpleName() + "] ";
    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;
    private final GeneratorIdentifierService generatorIdentifierService;

    public TopicService(
            final TopicRepository topicRepository,
            final CommentRepository commentRepository,
            final GeneratorIdentifierService generatorIdentifierService
    ) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
        this.generatorIdentifierService = generatorIdentifierService;
    }

    public Topic save(final User user, final TopicRequest topicRequest) {
        final var topic = topicRequest.toDomain(user, generatorIdentifierService.topicId());
        logger.info(classNameLogger + "starting the process of registering a topic with id {} and title {}", topic.id(), topic.title());
        this.topicRepository.save(topic);
        logger.info(classNameLogger + "finalizing the process of registering a topic with id {} and title {}", topic.id(), topic.title());
        return topic;
    }

    public Pair<TopicFindByIdWithUser, List<CommentFindById>> findByIdWithUserAndComments(final String id) {
        TopicFindByIdWithUser topicFindByIdWithUser;

        try {
            logger.info(classNameLogger + "starting process to fetch topic from id {}", id);
            topicFindByIdWithUser = this.topicRepository.findByIdWithUser(id);
            logger.info(classNameLogger + "finalizing process to fetch topic from id {}", id);
        } catch (EmptyResultDataAccessException ex) {
            logger.info(classNameLogger + "topic id {} does not exist", id);
            throw new NotFoundException(Topic.class, id);
        }

        logger.info(classNameLogger + "starting process to fetch topic comments from id {}", id);
        final var comments = commentRepository.findByIdWithUser(id);
        logger.info(classNameLogger + "finalizing process to fetch the topic comments from id {}", id);
        logger.info(classNameLogger + "number of comments returned {}", comments.size());
        return Pair.of(topicFindByIdWithUser, comments);
    }

    public TopicFindById findById(final String id) {
        try {
            logger.info(classNameLogger + "starting process to fetch topic from id {}", id);
            final var topic = this.topicRepository.findById(id);
            logger.info(classNameLogger + "finalizing process to fetch topic from id {}", id);
            return topic;
        } catch (EmptyResultDataAccessException ex) {
            logger.info(classNameLogger + "topic id {} does not exist", id);
            throw new NotFoundException(Topic.class, id);
        }
    }

}