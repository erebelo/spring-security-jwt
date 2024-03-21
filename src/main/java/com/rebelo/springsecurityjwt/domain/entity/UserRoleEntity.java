package com.rebelo.springsecurityjwt.domain.entity;

import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private RoleEnum role;

    public UserRoleEntity(RoleEnum role) {
        this.role = role;
    }
}
