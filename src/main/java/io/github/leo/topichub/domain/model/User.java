package io.github.leo.topichub.domain.model;

import io.github.leo.topichub.domain.valueobject.AccountStatus;
import io.github.leo.topichub.domain.valueobject.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @Setter
    private String name;

    @Setter
    private String email;

    @Setter
    private String password;

    private Role role = Role.USER;

    private AccountStatus status = AccountStatus.ACTIVE;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private String lastDeactivatedBy;
    private Instant lastDeactivatedAt;

    private Instant lastReactivatedAt;

    private Instant lastSuspendedAt;
    private String lastSuspendedBy;

    public void updateRole(Role role) {
        this.role = role;
    }

    public void deactivate(String userId) {
        this.lastDeactivatedAt = Instant.now();
        this.lastDeactivatedBy = userId;
        this.status = AccountStatus.DEACTIVATED;
    }

    public void reactivate() {
        this.lastReactivatedAt = Instant.now();
        this.status = AccountStatus.ACTIVE;
    }

    public void suspend(String moderatorId) {
        this.status = AccountStatus.SUSPENDED;
        this.lastSuspendedAt = Instant.now();
        this.lastSuspendedBy = moderatorId;
    }

    public void unsuspend() {
        this.status = AccountStatus.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return status != AccountStatus.SUSPENDED;
    }
}
