package io.github.leo.topichub.repository;

import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.domain.valueobject.AccountStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndStatus(String userId, AccountStatus accountStatus);
}
