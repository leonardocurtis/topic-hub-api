package io.github.leo.topichub.domain.model;

import io.github.leo.topichub.domain.valueobject.ModerationReason;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "responses")
public class Response {

    @Id
    private String id;

    @Setter
    private String topicId;

    @Setter
    private String message;

    @Setter
    private String authorId;

    @CreatedDate
    private Instant createdAt;

    @Setter
    private String deactivatedBy;

    @Setter
    private ModerationReason deactivationReason;

    private Instant deactivatedAt;

    @Field("isActive")
    private boolean active = true;

    public void deactivate(String moderatorId, ModerationReason reason) {
        this.active = false;
        this.deactivatedAt = Instant.now();
        this.deactivatedBy = moderatorId;
        this.deactivationReason = reason;
    }
}
