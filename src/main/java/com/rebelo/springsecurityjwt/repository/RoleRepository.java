package com.rebelo.springsecurityjwt.repository;

import com.rebelo.springsecurityjwt.domain.entity.UserRoleEntity;
import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByRole(RoleEnum role);

}
