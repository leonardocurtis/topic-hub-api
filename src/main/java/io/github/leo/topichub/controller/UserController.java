package io.github.leo.topichub.controller;

import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.dto.request.ChangePasswordRequest;
import io.github.leo.topichub.dto.request.DeactivateAccountRequest;
import io.github.leo.topichub.dto.request.UpdateUserRequest;
import io.github.leo.topichub.dto.response.UpdateUserProfileResponse;
import io.github.leo.topichub.dto.response.UserProfileResponse;
import io.github.leo.topichub.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
@Tag(name = "Users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal User user) {

        var response = userService.getMe(user.getId());

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping()
    public ResponseEntity<UpdateUserProfileResponse> updateMyProfile(@RequestBody @Valid UpdateUserRequest request) {

        var updatedUser = userService.updateMe(request);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<Void> deactivateMyProfile(@RequestBody @Valid DeactivateAccountRequest request) {

        userService.deactivateMyAccount(request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.noContent().build();
    }
}
