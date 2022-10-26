package br.com.forum.domain.service;

import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.CommentRepository;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.web.controller.request.CommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.forum.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class CommentServiceTest {

    private final CommentRepository commentRepository = mock(CommentRepository.class);
    private final TopicService topicService = mock(TopicService.class);
    private final GeneratorIdentifierService generatorIdentifierService = mock(GeneratorIdentifierService.class);

    private final CommentService service = new CommentService(commentRepository, topicService, generatorIdentifierService);

    private User user;
    private TopicFindById topicFindById;
    private CommentRequest commentRequest;
    private String id;

    @BeforeEach
    void setUp() {
        user = createUser("email");

        topicFindById = createTopicFindById(createTopic(user, null));

        commentRequest = createCommentRequest();

        id = "id";
    }

    @Test
    void when_you_receive_a_request_to_register_a_comment_you_must_register_successfully() {
        when(generatorIdentifierService.commentId()).thenReturn(id);
        when(topicService.findById(topicFindById.id())).thenReturn(topicFindById);

        final var returnComment = service.save(user, topicFindById.id(), commentRequest);

        assertEquals(returnComment.id(), id);

        verify(generatorIdentifierService, only()).commentId();
        verify(topicService, only()).findById(topicFindById.id());
        verify(commentRepository, only()).save(any());
    }
}