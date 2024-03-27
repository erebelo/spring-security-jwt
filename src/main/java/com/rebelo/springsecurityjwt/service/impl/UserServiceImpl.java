package com.rebelo.springsecurityjwt.service.impl;

import com.rebelo.springsecurityjwt.config.DbLoaderConfiguration;
import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import com.rebelo.springsecurityjwt.exception.DuplicationException;
import com.rebelo.springsecurityjwt.exception.NotFoundException;
import com.rebelo.springsecurityjwt.mapper.UserMapper;
import com.rebelo.springsecurityjwt.repository.UserRepository;
import com.rebelo.springsecurityjwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.rebelo.springsecurityjwt.util.AuthorizationUtil.getAuthenticatedUsername;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return this.findEntityByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<UserResponse> findAll() {
        var userEntityList = repository.findAll();
        return mapper.entityListToResponseList(userEntityList);
    }

    @Override
    public UserResponse findById(Long id) {
        var userEntity = validateIdentityAndRetrievingUserEntity(id);
        return mapper.entityToResponse(userEntity);
    }

    @Override
    public UserResponse findByEmail(String email) {
        var userEntity = validateIdentityAndRetrievingUserEntity(email);
        return mapper.entityToResponse(userEntity);
    }

    @Override
    public UserResponse insert(UserCreateRequest userCreateRequest) {
        checkEmailDuplication(userCreateRequest.getEmail());

        var userEntity = mapper.createRequestToEntity(userCreateRequest);
        userEntity.addRole(DbLoaderConfiguration.getRoleByName(RoleEnum.USER));

        userEntity = repository.save(userEntity);
        return mapper.entityToResponse(userEntity);
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        var userEntity = validateIdentityAndRetrievingUserEntity(id);
        checkEmailDuplication(userRequest.getEmail());

        var newUserEntity = mapper.requestToEntity(userRequest, userEntity);

        newUserEntity = repository.save(newUserEntity);
        return mapper.entityToResponse(newUserEntity);
    }

    @Override
    public void delete(Long id) {
        var userEntity = this.findEntityById(id);
        repository.delete(userEntity);
    }

    public UserEntity findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found by id: " + id));
    }

    public UserEntity findEntityByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found by email: " + email));
    }

    private UserEntity validateIdentityAndRetrievingUserEntity(Long id) {
        var authenticatedEmail = getAuthenticatedUsername();
        var userEntity = this.findEntityByEmail(authenticatedEmail);

        if (Objects.equals(userEntity.getId(), id)) {
            return userEntity;
        }

        throw new IllegalStateException("Data inconsistency: Invalid provided credentials");
    }

    private UserEntity validateIdentityAndRetrievingUserEntity(String email) {
        var authenticatedEmail = getAuthenticatedUsername();

        if (Objects.equals(authenticatedEmail, email)) {
            return this.findEntityByEmail(authenticatedEmail);
        }

        throw new IllegalStateException("Data inconsistency: Invalid provided credentials");
    }

    private void checkEmailDuplication(String email) {
        var userEntityFound = repository.findByEmail(email).orElse(null);

        if (Objects.nonNull(userEntityFound)) {
            throw new DuplicationException("Email already in use: " + email);
        }
    }
}
