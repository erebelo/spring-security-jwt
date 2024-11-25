package com.rebelo.springsecurityjwt.domain.entity;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.ROLE_PREFIX;
import static com.rebelo.springsecurityjwt.util.MaskUtil.maskEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class UserEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "name is mandatory")
    @Size(min = 2, max = 50, message = "name must be between 6 to 50 characters")
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email(message = "Invalid email address")
    @Size(min = 6, max = 50, message = "email must be between 6 to 50 characters")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotEmpty(message = "roles is mandatory")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES")
    private Set<UserRoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRoleEntity role : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole().getCode().toUpperCase()));
        }

        return authorities;
    }

    public void addRole(UserRoleEntity role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }

        this.roles.add(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", name='" + name + '\'' + ", email='" + maskEmail(email) + '\''
                + ", password='*****'" + ", roles='*****'" + '}';
    }
}
