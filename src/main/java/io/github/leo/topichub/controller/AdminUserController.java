package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.ChangeRoleRequest;
import io.github.leo.topichub.dto.response.ChangeRoleResponse;
import io.github.leo.topichub.dto.response.SuspendUserResponse;
import io.github.leo.topichub.dto.response.UnsuspendUserResponse;
import io.github.leo.topichub.dto.response.UserDetailsResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@Tag(
        name = "Admin Users",
        description =
                "Administrative endpoints for managing user accounts. Only accessible by users with the ADMIN role.")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Change user role", description = """
        Updates the role of a user.

        Only administrators can perform this operation.
        Common roles include:

        1. USER

        2. MODERATOR

        3. ADMIN
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User role updated successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ChangeRoleResponse.class)))
    })
    @PutMapping("/{id}/role")
    public ResponseEntity<ChangeRoleResponse> changeUserRole(
            @Parameter(description = "Unique identifier of the user") @PathVariable String id,
            @RequestBody @Valid ChangeRoleRequest dto) {

        var user = userService.changeRole(id, dto);

        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Get user details", description = "Returns detailed information about a specific user.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User found",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserDetailsResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getUser(
            @Parameter(description = "Unique identifier of the user") @PathVariable String id) {

        var user = userService.getUserDetails(id);

        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Suspend user account", description = """
        Suspends a user account.

        A suspended user cannot:

        1. Create topics

        2. Post replies

        3. Interact with the forum
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User suspended successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SuspendUserResponse.class)))
    })
    @PatchMapping("/{id}/suspend")
    public ResponseEntity<SuspendUserResponse> suspendUser(
            @Parameter(description = "Unique identifier of the user") @PathVariable String id) {

        var response = userService.suspendUser(id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Unsuspend user account", description = "Reactivates a previously suspended user account.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User unsuspended successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UnsuspendUserResponse.class)))
    })
    @PatchMapping("/{id}/unsuspend")
    public ResponseEntity<UnsuspendUserResponse> unsuspendUser(
            @Parameter(description = "Unique identifier of the user") @PathVariable String id) {

        var response = userService.unsuspendUser(id);

        return ResponseEntity.ok(response);
    }
}
