package io.github.leo.topichub.controller;

import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.dto.request.ChangePasswordRequest;
import io.github.leo.topichub.dto.request.DeactivateAccountRequest;
import io.github.leo.topichub.dto.request.UpdateUserRequest;
import io.github.leo.topichub.dto.response.UpdateUserProfileResponse;
import io.github.leo.topichub.dto.response.UserProfileResponse;
import io.github.leo.topichub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
@Tag(name = "Users", description = "Endpoints for managing the authenticated user's profile")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user profile", description = """
        Returns the profile information of the authenticated user

        The user is identified through the JWT token
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User profile retrieved successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserProfileResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        var response = userService.getMe(user.getId());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update user profile", description = """
        Updates the authenticated user's profile information

        Only provided fields will be updated
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Profile updated successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UpdateUserProfileResponse.class)))
    })
    @PatchMapping()
    public ResponseEntity<UpdateUserProfileResponse> updateMyProfile(@RequestBody @Valid UpdateUserRequest request) {

        var updatedUser = userService.updateMe(request);

        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Deactivate user account", description = """
        Deactivates the authenticated user's account.

        This is a temporary action initiated by the user.
        The account will be automatically reactivated upon the next successful login.
        While deactivated, access to the platform is restricted.
        """)
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Account deactivated successfully")})
    @DeleteMapping("/deactivate")
    public ResponseEntity<Void> deactivateMyProfile(@RequestBody @Valid DeactivateAccountRequest request) {

        userService.deactivateMyAccount(request);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change password", description = """
        Updates the authenticated user's password

        The current password must be provided for security reasons
        """)
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Password updated successfully")})
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.noContent().build();
    }
}
