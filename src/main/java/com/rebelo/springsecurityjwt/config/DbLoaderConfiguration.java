package com.rebelo.springsecurityjwt.config;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.entity.UserRoleEntity;
import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import com.rebelo.springsecurityjwt.repository.RoleRepository;
import com.rebelo.springsecurityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class DbLoaderConfiguration {

    @Value("${admin.user.password}")
    private String adminPassword;

    private static Set<UserRoleEntity> userRoles;

    @Bean
    public CommandLineRunner populateDatabaseAndLoadRoles(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            List<UserRoleEntity> roles = new ArrayList<>();
            roles.add(new UserRoleEntity(RoleEnum.ADMIN));
            roles.add(new UserRoleEntity(RoleEnum.USER));

            roleRepository.saveAll(roles);

            userRoles = new HashSet<>(roleRepository.findAll());

            var user = UserEntity.builder()
                    .name("Admin")
                    .email("admin@mail.com")
                    .password(passwordEncoder.encode(adminPassword))
                    .roles(userRoles)
                    .build();

            userRepository.save(user);
        };
    }

    public static UserRoleEntity getRoleByName(RoleEnum roleName) {
        return userRoles.stream()
                .filter(role -> role.getRole().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Role with ID " + roleName.getCode() + " not found"));
    }
}
