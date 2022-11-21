package br.com.forum.web.controller.response;

import br.com.forum.domain.repository.mapper.response.TopicFindAll;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class TopicFindAllResponse {

    public static List<TopicFindAllResponse> toResponse(final List<TopicFindAll> topicFindAll) {
        return topicFindAll.stream().map(m -> new TopicFindAllResponse(m.getId(), m.getTitle(), m.getCreationDate().toString())).collect(Collectors.toList());
    }

    private String id;
    private String title;
    private String creationDate;

    public TopicFindAllResponse(final String id, final String title, final String creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
