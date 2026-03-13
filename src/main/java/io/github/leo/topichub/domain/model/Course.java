package io.github.leo.topichub.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    @Setter
    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @Setter
    private List<String> categoryIds;

    @Field("isActive")
    private boolean active = true;

    public void deactivate() {
        this.active = false;
    }
}
