package io.github.leo.topichub.repository;

import io.github.leo.topichub.domain.model.Topic;
import io.github.leo.topichub.domain.valueobject.TopicStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface TopicRepository extends MongoRepository<Topic, String> {
    Optional<Topic> findByIdAndActiveTrue(String id);

    Page<Topic> findAllByActiveTrue(Pageable pp);

    @Query("""
        {
        '_id': ?0,
        'isActive': true,
        'status': ?1
        }
        """)
    Optional<Topic> findEditableTopic(String id, TopicStatus status);
}
