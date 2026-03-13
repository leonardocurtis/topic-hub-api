package io.github.leo.topichub.repository;

import io.github.leo.topichub.domain.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Course> findByIdAndActiveTrue(String id);

    boolean existsByCategoryIdsContaining(String id);
}
