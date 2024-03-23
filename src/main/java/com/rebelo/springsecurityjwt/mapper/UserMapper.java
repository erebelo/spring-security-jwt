package com.rebelo.springsecurityjwt.mapper;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.WARN;

@Mapper(componentModel = "spring", unmappedTargetPolicy = WARN)
public interface UserMapper {

    List<UserResponse> entityListToResponseList(List<UserEntity> userEntityList);

    UserResponse entityToResponse(UserEntity userEntity);

    UserEntity requestToEntity(UserCreateRequest userCreateRequest);

    UserEntity requestToEntity(UserRequest userRequest);

}

