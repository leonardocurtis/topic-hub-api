package io.github.leo.topichub.domain.model;

import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "topics")
public class Topic {

    @Id
    private String id;

    @Setter
    private String title;

    @Setter
    private TopicType type;

    @Setter
    private String message;

    @CreatedDate
    private LocalDateTime createdAt;

    @Setter
    private TopicStatus status;

    @Setter
    private String authorId;

    @Setter
    private String courseId;

    @Setter
    private String solvedResponseId;

    @Field("isActive")
    private boolean active = true;

    public void deactivate() {
        this.active = false;
        this.status = TopicStatus.CLOSED;
    }

    public void markAsSolved(String responseId) {
        this.solvedResponseId = responseId;
        this.status = TopicStatus.SOLVED;
    }

    public void reopen() {
        this.solvedResponseId = null;
        this.status = TopicStatus.OPEN;
    }

    public void markAsOpen() {
        this.status = TopicStatus.OPEN;
    }

    public void handleSolutionRemoval(String responseId) {
        if (responseId.equals(this.solvedResponseId)) {
            reopen();
        }
    }
}
