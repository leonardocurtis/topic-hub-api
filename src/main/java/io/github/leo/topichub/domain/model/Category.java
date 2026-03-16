package io.github.leo.topichub.domain.model;

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
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    @Setter
    private String name;

    @CreatedDate
    private Instant createdAt;

    @Field("isActive")
    private boolean active = true;

    public void deactivate() {
        this.active = false;
    }
}
