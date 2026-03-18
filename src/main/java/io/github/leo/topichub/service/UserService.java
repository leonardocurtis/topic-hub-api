package io.github.leo.topichub.service;

import io.github.leo.topichub.config.security.TokenService;
import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;
import io.github.leo.topichub.dto.request.*;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.exception.ConflictException;
import io.github.leo.topichub.exception.ForbiddenException;
import io.github.leo.topichub.exception.InvalidPasswordException;
import io.github.leo.topichub.exception.ResourceNotFoundException;
import io.github.leo.topichub.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final AuthenticationManager manager;

    public UserService(
            UserRepository repository,
            PasswordEncoder encoder,
            TokenService tokenService,
            AuthenticationManager manager) {
        this.repository = repository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.manager = manager;
    }

    public CreateUserResponse registerUser(CreateUserRequest dto) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new ConflictException("Email " + dto.email() + " is already taken");
        }

        var user = new User();

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));

        var savedUser = repository.save(user);

        return new CreateUserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public ChangeRoleResponse changeRole(String id, ChangeRoleRequest dto) {
        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("You cannot change your own role");
        }

        var user = repository
                .findByIdAndStatus(id, AccountStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.updateRole(dto.role());

        var savedUser = repository.save(user);

        return new ChangeRoleResponse(savedUser.getId(), savedUser.getName(), savedUser.getRole());
    }

    public UserDetailsResponse getUserDetails(String id) {

        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserDetailsResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getLastDeactivatedBy(),
                user.getLastDeactivatedAt(),
                user.getUpdatedAt(),
                user.getStatus(),
                user.getLastReactivatedAt(),
                user.getLastSuspendedAt(),
                user.getLastSuspendedBy());
    }

    public SuspendUserResponse suspendUser(String id) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String moderatorId = authenticatedUser.getId();

        if (moderatorId.equals(id)) {
            throw new ForbiddenException("Admin cannot suspend their own account");
        }

        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() == (Role.ADMIN)) {
            throw new ForbiddenException("Cannot suspend admin users");
        }

        if (user.getStatus() == AccountStatus.SUSPENDED) {
            throw new ConflictException("User already suspended");
        }

        user.suspend(moderatorId);
        var savedUser = repository.save(user);

        return new SuspendUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getStatus(),
                savedUser.getCreatedAt(),
                savedUser.getLastSuspendedAt(),
                savedUser.getLastSuspendedBy());
    }

    public UserProfileResponse getMe(String userId) {

        var user = repository
                .findByIdAndStatus(userId, AccountStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserProfileResponse(user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }

    public UpdateUserProfileResponse updateMe(UpdateUserRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = authenticatedUser.getId();

        var user = repository
                .findByIdAndStatus(userId, AccountStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(request.name());

        var savedUser = repository.save(user);

        return new UpdateUserProfileResponse(
                savedUser.getName(), savedUser.getEmail(), savedUser.getCreatedAt(), savedUser.getUpdatedAt());
    }

    public void changePassword(ChangePasswordRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = repository
                .findByIdAndStatus(authenticatedUser.getId(), AccountStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean passwordMatches = encoder.matches(request.currentPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        user.setPassword(encoder.encode(request.newPassword()));

        repository.save(user);
    }

    public void deactivateMyAccount(DeactivateAccountRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = repository
                .findById(authenticatedUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.deactivate(user.getId());

        repository.save(user);
    }

    public LoginResponse login(LoginRequest dto) {

        var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        var user = (User) authentication.getPrincipal();

        if (user.getStatus() == AccountStatus.DEACTIVATED) {
            user.reactivate();
            repository.save(user);
        }

        var token = tokenService.generateToken(user);

        return new LoginResponse(token);
    }

    public UnsuspendUserResponse unsuspendUser(String id) {

        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus() != AccountStatus.SUSPENDED) {
            throw new ConflictException("User is not suspended");
        }

        user.unsuspend();
        var savedUser = repository.save(user);

        return new UnsuspendUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getStatus(),
                savedUser.getCreatedAt());
    }
}
