package com.rebelo.springsecurityjwt.config;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.entity.UserRoleEntity;
import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import com.rebelo.springsecurityjwt.repository.RoleRepository;
import com.rebelo.springsecurityjwt.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DbLoaderConfiguration {

    private static Set<UserRoleEntity> roles;

    @Bean
    public CommandLineRunner populateDatabaseAndInitializeRoles(UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder,
            @Value("${admin.user.password}") String adminPassword) {
        return args -> {
            List<UserRoleEntity> newRoles = new ArrayList<>();
            newRoles.add(new UserRoleEntity(RoleEnum.ADMIN));
            newRoles.add(new UserRoleEntity(RoleEnum.USER));

            roles = Set.copyOf(roleRepository.saveAll(newRoles));

            UserEntity user = UserEntity.builder().name("Admin").email("admin@mail.com")
                    .password(passwordEncoder.encode(adminPassword)).roles(DbLoaderConfiguration.roles).build();

            userRepository.save(user);
        };
    }

    public static UserRoleEntity getRoleByName(RoleEnum roleName) {
        if (roles == null) {
            throw new IllegalStateException("User roles have not been initialized.");
        }

        return roles.stream().filter(role -> role.getRole().equals(roleName)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Role with ID " + roleName.getCode() + " not found"));
    }
}
