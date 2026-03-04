package io.github.leo.topichub.repository;

import io.github.leo.topichub.domain.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Boolean existsByName(String name);
}
