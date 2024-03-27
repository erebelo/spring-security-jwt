package com.rebelo.springsecurityjwt.mapper;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.domain.request.UserCreateRequest;
import com.rebelo.springsecurityjwt.domain.request.UserRequest;
import com.rebelo.springsecurityjwt.domain.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.WARN;

@Mapper(componentModel = "spring", unmappedTargetPolicy = WARN)
public interface UserMapper {

    List<UserResponse> entityListToResponseList(List<UserEntity> userEntityList);

    UserResponse entityToResponse(UserEntity userEntity);

    UserEntity createRequestToEntity(UserCreateRequest userCreateRequest);

    @Mapping(target = "id", source = "userEntity.id")
    @Mapping(target = "name", source = "userRequest.name")
    @Mapping(target = "email", source = "userRequest.email")
    @Mapping(target = "password", source = "userEntity.password")
    @Mapping(target = "roles", source = "userEntity.roles")
    UserEntity requestToEntity(UserRequest userRequest, UserEntity userEntity);

}

