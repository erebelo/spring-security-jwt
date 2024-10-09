package com.rebelo.springsecurityjwt.service;

import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    UserResponse insert(UserCreateRequest userRequest);

    UserResponse update(Long id, UserRequest userRequest);

    void delete(Long id);

}
