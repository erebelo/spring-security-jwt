package com.rebelo.springsecurityjwt.service.impl;

import static com.rebelo.springsecurityjwt.util.AuthorizationUtil.getAuthenticatedUsername;

import com.rebelo.springsecurityjwt.config.DbLoaderConfiguration;
import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.exception.model.NotFoundException;
import com.rebelo.springsecurityjwt.exception.model.UnprocessableEntityException;
import com.rebelo.springsecurityjwt.mapper.UserMapper;
import com.rebelo.springsecurityjwt.repository.UserRepository;
import com.rebelo.springsecurityjwt.service.UserService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Data inconsistency: Invalid provided credentials";
    private static final String DUPLICATED_EMAIL_ERROR_MESSAGE = "Email already in use: %s";
    private static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found by %s: %s";

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        try {
            return this.findEntityByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        log.info("Fetching all users");
        var userEntityList = repository.findAll();

        log.info("Users successfully retrieved: {}", userEntityList);
        return mapper.entityListToResponseList(userEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        log.info("Fetching user with id: {}", id);
        var userEntity = validateIdentityById(id);

        log.info("User successfully retrieved: {}", userEntity);
        return mapper.entityToResponse(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        var userEntity = validateIdentityByEmail(email);

        log.info("User successfully retrieved: {}", userEntity);
        return mapper.entityToResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponse insert(UserCreateRequest userCreateRequest) {
        log.info("Creating user");
        checkEmailDuplication(userCreateRequest.getEmail());

        var userEntity = mapper.createRequestToEntity(userCreateRequest);
        userEntity.addRole(DbLoaderConfiguration.getRoleByName(RoleEnum.USER));
        userEntity = repository.save(userEntity);

        log.info("User created successfully: {}", userEntity);
        return mapper.entityToResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("Updating user with id: {}", id);
        var userEntity = validateIdentityById(id);
        checkEmailDuplication(userRequest.getEmail());

        var newUserEntity = mapper.requestToEntity(userRequest, userEntity);
        newUserEntity = repository.save(newUserEntity);

        log.info("User updated successfully: {}", newUserEntity);
        return mapper.entityToResponse(newUserEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        var userEntity = this.findEntityById(id);

        repository.delete(userEntity);
        log.info("User deleted successfully");
    }

    public UserEntity findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_ERROR_MESSAGE, "id", id)));
    }

    public UserEntity findEntityByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_ERROR_MESSAGE, "email", email)));
    }

    private UserEntity validateIdentityById(Long id) {
        var authenticatedEmail = getAuthenticatedUsername();
        var userEntity = this.findEntityByEmail(authenticatedEmail);

        if (Objects.equals(userEntity.getId(), id)) {
            return userEntity;
        }

        throw new UnprocessableEntityException(INVALID_CREDENTIALS_ERROR_MESSAGE);
    }

    private UserEntity validateIdentityByEmail(String email) {
        var authenticatedEmail = getAuthenticatedUsername();

        if (Objects.equals(authenticatedEmail, email)) {
            return this.findEntityByEmail(authenticatedEmail);
        }

        throw new UnprocessableEntityException(INVALID_CREDENTIALS_ERROR_MESSAGE);
    }

    private void checkEmailDuplication(String email) {
        var userEntityFound = repository.findByEmail(email).orElse(null);

        if (Objects.nonNull(userEntityFound)) {
            throw new UnprocessableEntityException(String.format(DUPLICATED_EMAIL_ERROR_MESSAGE, email));
        }
    }
}
