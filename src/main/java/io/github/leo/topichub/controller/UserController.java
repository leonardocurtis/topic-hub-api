package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.ChangeRoleRequest;
import io.github.leo.topichub.dto.response.ChangeRoleResponse;
import io.github.leo.topichub.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChangeRoleResponse> changeUserRole(@PathVariable String id, @RequestBody ChangeRoleRequest dto) {

        var user = userService.changeRole(id, dto);

        return ResponseEntity.ok().body(user);
    }
}
