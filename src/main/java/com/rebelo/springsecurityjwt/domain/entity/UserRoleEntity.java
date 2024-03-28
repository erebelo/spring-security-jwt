package com.rebelo.springsecurityjwt.domain.entity;

import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class UserRoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "role is mandatory")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private String description;

    public UserRoleEntity(RoleEnum role) {
        this.role = role;
        this.description = role.getValue();
    }
}
