package br.com.forum.utils;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.mapper.response.CommentFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import br.com.forum.domain.repository.mapper.response.UserFindById;
import br.com.forum.domain.service.GeneratorIdentifierService;

import java.time.LocalDateTime;
import java.util.List;

public final class TestUtils {

    private static final GeneratorIdentifierService generatorIdentifierService = new GeneratorIdentifierService();

    public static User createUser(final String email) {
        return new User(generatorIdentifierService.userId(), "name", email, "password", "profilePicture");
    }

    public static UserFindById createUserFindById(final User user) {
        return new UserFindById(user.id(), user.name(), user.email(), user.profilePicture());
    }

    public static Topic createTopic(final User user, final List<Comment> comments) {
        return new Topic(generatorIdentifierService.topicId(), "title", "contents", LocalDateTime.now(), LocalDateTime.now(), user, comments);
    }

    public static TopicFindById createTopicFindById(final Topic topic) {
        return new TopicFindById(topic.id(), topic.title(), topic.contents(), topic.creationDate(), topic.updateDate(), new TopicFindById.User(topic.user().name(), topic.user().profilePicture()));
    }

    public static Comment createComment(final Topic topic, final User user) {
        return new Comment(generatorIdentifierService.commentId(), "contents", LocalDateTime.now(), LocalDateTime.now(), topic, user);
    }

    public static CommentFindById createCommentFindById(final Comment comment) {
        return new CommentFindById(comment.id(), comment.contents(), comment.creationDate(), comment.updateDate(), new CommentFindById.User(comment.user().name(), comment.user().profilePicture()));
    }
}