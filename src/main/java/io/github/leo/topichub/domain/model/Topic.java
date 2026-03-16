package io.github.leo.topichub.domain.model;

import io.github.leo.topichub.domain.valueobject.TopicCloseReason;
import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.domain.valueobject.TopicType;
import io.github.leo.topichub.exception.ConflictException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

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
    private Instant createdAt;

    @Setter
    private TopicStatus status;

    @Setter
    private String authorId;

    @Setter
    private String courseId;

    @Setter
    private String lastSolvedResponseId;

    private TopicCloseReason closedReason;
    private String closedBy;
    private Instant closedAt;

    public void suspend() {
        this.status = TopicStatus.SUSPENDED;
    }

    public void close(String userId, TopicCloseReason reason) {

        if (this.status == TopicStatus.SUSPENDED) {
            throw new ConflictException("Topic suspended cannot be closed");
        }

        if (this.status == TopicStatus.CLOSED) {
            throw new ConflictException("Topic is already closed");
        }

        this.status = TopicStatus.CLOSED;
        this.closedAt = Instant.now();
        this.closedBy = userId;
        this.closedReason = reason;
    }

    public void markAsSolved(String responseId) {
        this.lastSolvedResponseId = responseId;
        this.status = TopicStatus.SOLVED;
    }

    public void reopen() {

        if (this.status == TopicStatus.CLOSED) {
            throw new ConflictException("Closed topics cannot be reopened");
        }
        this.status = TopicStatus.OPEN;
    }

    public void markAsOpen() {
        this.status = TopicStatus.OPEN;
    }

    public void handleSolutionRemoval(String responseId) {
        if (this.lastSolvedResponseId != null && this.lastSolvedResponseId.equals(responseId)) {
            this.lastSolvedResponseId = null;
            reopen();
        }
    }
}
