package com.rebelo.springsecurityjwt.service;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findById(Long id);

    UserEntity findByEmail(String email);

    List<UserEntity> findAll();

    UserEntity create(UserEntity user);

    UserEntity update(UserEntity user);

    void delete(Long id);

}
