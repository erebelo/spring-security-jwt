package com.rebelo.springsecurityjwt.config;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.entity.UserRoleEntity;
import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import com.rebelo.springsecurityjwt.repository.RoleRepository;
import com.rebelo.springsecurityjwt.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DbLoaderConfiguration {

    @Bean
    public CommandLineRunner populateDatabaseAndInitializeRoles(UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder,
            @Value("${admin.user.password}") String adminPassword) {
        return args -> {
            List<UserRoleEntity> roles = new ArrayList<>();
            roles.add(new UserRoleEntity(RoleEnum.ADMIN));
            roles.add(new UserRoleEntity(RoleEnum.USER));

            Set<UserRoleEntity> savedRoles = new HashSet<>(roleRepository.saveAll(roles));

            UserEntity user = UserEntity.builder().name("Admin").email("admin@mail.com")
                    .password(passwordEncoder.encode(adminPassword)).roles(savedRoles).build();

            userRepository.save(user);
        };
    }
}
