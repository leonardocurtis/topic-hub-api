package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.ChangeRoleRequest;
import io.github.leo.topichub.dto.response.ChangeRoleResponse;
import io.github.leo.topichub.dto.response.SuspendUserResponse;
import io.github.leo.topichub.dto.response.UnsuspendUserResponse;
import io.github.leo.topichub.dto.response.UserDetailsResponse;
import io.github.leo.topichub.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@Tag(name = "Admin Users")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<ChangeRoleResponse> changeUserRole(
            @PathVariable String id, @RequestBody @Valid ChangeRoleRequest dto) {

        var user = userService.changeRole(id, dto);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable String id) {

        var user = userService.getUserDetails(id);

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<SuspendUserResponse> suspendUser(@PathVariable String id) {

        var response = userService.suspendUser(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/unsuspend")
    public ResponseEntity<UnsuspendUserResponse> unsuspendUser(@PathVariable String id) {

        var response = userService.unsuspendUser(id);

        return ResponseEntity.ok(response);
    }
}
