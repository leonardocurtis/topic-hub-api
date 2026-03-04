package io.github.leo.topichub.repository;

import io.github.leo.topichub.domain.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TopicRepository extends MongoRepository<Topic, String> {
    Optional<Topic> findByIdAndActiveTrue(String id);
}
