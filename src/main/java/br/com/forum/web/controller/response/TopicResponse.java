package br.com.forum.web.controller.response;

import br.com.forum.domain.repository.mapper.response.CommentFindById;
import br.com.forum.domain.repository.mapper.response.TopicFindByIdWithUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.flywaydb.core.internal.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class TopicResponse {

    public static TopicResponse toResponse(Pair<TopicFindByIdWithUser, List<CommentFindById>> pair) {
        return new TopicResponse(
                pair.getLeft().title(),
                pair.getLeft().contents(),
                UserResponse.toResponse(pair.getLeft().user()),
                pair.getRight().stream().map(CommentResponse::toResponse).collect(Collectors.toList())
        );
    }

    private String tittle;
    private String content;
    private UserResponse author;
    private List<CommentResponse> comment;

    public TopicResponse(
            final String tittle,
            final String content,
            final UserResponse author,
            final List<CommentResponse> comment
    ) {
        this.tittle = tittle;
        this.content = content;
        this.author = author;
        this.comment = comment;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public void setAuthor(UserResponse author) {
        this.author = author;
    }

    public List<CommentResponse> getComment() {
        return comment;
    }

    public void setComment(List<CommentResponse> comment) {
        this.comment = comment;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class UserResponse {

        private static UserResponse toResponse(TopicFindByIdWithUser.User user) {
            return new UserResponse(user.name(), user.profilePicture());
        }

        private static UserResponse toResponse(CommentFindById.User user) {
            return new UserResponse(user.name(), user.profilePicture());
        }

        private String name;
        private String image;

        public UserResponse(String name, String image) {
            this.name = name;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class CommentResponse {

        private static CommentResponse toResponse(CommentFindById commentFindById) {
            return new CommentResponse(
                    commentFindById.contents(),
                    UserResponse.toResponse(commentFindById.user()),
                    commentFindById.creationDate().toString()
            );
        }

        private String content;
        private UserResponse author;
        private String dateCreation;

        public CommentResponse(String content, UserResponse author, String dateCreation) {
            this.content = content;
            this.author = author;
            this.dateCreation = dateCreation;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public UserResponse getAuthor() {
            return author;
        }

        public void setAuthor(UserResponse author) {
            this.author = author;
        }

        public String getDateCreation() {
            return dateCreation;
        }

        public void setDateCreation(String dateCreation) {
            this.dateCreation = dateCreation;
        }
    }
}
