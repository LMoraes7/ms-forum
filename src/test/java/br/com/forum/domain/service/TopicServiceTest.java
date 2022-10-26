package br.com.forum.domain.service;

import br.com.forum.domain.exception.NotFoundException;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.CommentRepository;
import br.com.forum.domain.repository.TopicRepository;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindByIdWithUser;
import br.com.forum.web.controller.request.TopicRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static br.com.forum.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class TopicServiceTest {

    private final TopicRepository topicRepository = mock(TopicRepository.class);
    private final CommentRepository commentRepository = mock(CommentRepository.class);
    private final GeneratorIdentifierService generatorIdentifierService = mock(GeneratorIdentifierService.class);

    private final TopicService service = new TopicService(topicRepository, commentRepository, generatorIdentifierService);

    private User user;
    private TopicRequest topicRequest;
    private TopicFindByIdWithUser topicFindByIdWithUser;

    private TopicFindById topicFindById;

    @BeforeEach
    void setUp() {
        user = createUser("email");

        final var topic = createTopic(user, null);

        topicRequest = createTopicRequest();

        topicFindByIdWithUser = createTopicFindByIdWithUser(topic);

        topicFindById = createTopicFindById(topic);
    }

    @Test
    void when_you_receive_a_request_to_register_a_topic_you_must_register_successfully() {
        when(generatorIdentifierService.topicId()).thenReturn(topicFindByIdWithUser.id());

        final var returnTopic = service.save(user, topicRequest);

        assertEquals(returnTopic.title(), topicRequest.getTitle());
        assertEquals(returnTopic.contents(), topicRequest.getContent());

        verify(generatorIdentifierService, only()).topicId();
        verify(topicRepository, only()).save(any());
        verifyNoInteractions(commentRepository);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_topic_by_id_with_user_it_should_run_successfully() {
        when(topicRepository.findByIdWithUser(topicFindByIdWithUser.id())).thenReturn(topicFindByIdWithUser);
        when(commentRepository.findByIdWithUser(topicFindByIdWithUser.id())).thenReturn(List.of());

        final var pair = service.findByIdWithUserAndComments(topicFindByIdWithUser.id());

        assertEquals(pair.getLeft(), topicFindByIdWithUser);
        assertTrue(pair.getRight().isEmpty());

        verify(topicRepository, only()).findByIdWithUser(topicFindByIdWithUser.id());
        verify(commentRepository, only()).findByIdWithUser(topicFindByIdWithUser.id());
        verifyNoInteractions(generatorIdentifierService);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_topic_by_id_with_user_should_throw_a_NotFoundException_when_receiving_an_EmptyResultDataAccessException() {
        when(topicRepository.findByIdWithUser(topicFindByIdWithUser.id())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NotFoundException.class, () -> service.findByIdWithUserAndComments(topicFindByIdWithUser.id()));

        verify(topicRepository, only()).findByIdWithUser(topicFindByIdWithUser.id());
        verifyNoInteractions(generatorIdentifierService, commentRepository);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_topic_by_id_it_should_run_successfully() {
        when(topicRepository.findById(topicFindById.id())).thenReturn(topicFindById);

        final var returnTopic = service.findById(topicFindById.id());

        assertEquals(returnTopic, topicFindById);

        verify(topicRepository, only()).findById(topicFindById.id());
        verifyNoInteractions(generatorIdentifierService, commentRepository);
    }

    @Test
    void when_receiving_a_request_to_fetch_a_topic_by_id_should_throw_a_NotFoundException_when_receiving_an_EmptyResultDataAccessException() {
        when(topicRepository.findById(topicFindById.id())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NotFoundException.class, () -> service.findById(topicFindById.id()));

        verify(topicRepository, only()).findById(topicFindById.id());
        verifyNoInteractions(generatorIdentifierService, commentRepository);
    }
}