package io.github.leo.topichub.service;

import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.domain.valueobject.Role;
import io.github.leo.topichub.dto.request.ChangeRoleRequest;
import io.github.leo.topichub.dto.request.CreateUserRequest;
import io.github.leo.topichub.dto.response.ChangeRoleResponse;
import io.github.leo.topichub.dto.response.CreateUserResponse;
import io.github.leo.topichub.exception.ConflictException;
import io.github.leo.topichub.exception.ResourceNotFoundException;
import io.github.leo.topichub.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public CreateUserResponse registerUser(CreateUserRequest dto) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new ConflictException("Email already registered");
        }

        var user = new User();

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        user.setRoles(List.of(Role.USER));

        var savedUser = repository.save(user);

        return new CreateUserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public ChangeRoleResponse changeRole(String id, ChangeRoleRequest dto) {
        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRoles(List.of(dto.role()));

        var savedUser = repository.save(user);

        return new ChangeRoleResponse(savedUser.getId(), savedUser.getName(), savedUser.getRoles());
    }
}
