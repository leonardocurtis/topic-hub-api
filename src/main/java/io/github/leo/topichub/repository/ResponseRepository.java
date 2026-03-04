package io.github.leo.topichub.repository;

import io.github.leo.topichub.domain.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResponseRepository extends MongoRepository<Response, String> {}
