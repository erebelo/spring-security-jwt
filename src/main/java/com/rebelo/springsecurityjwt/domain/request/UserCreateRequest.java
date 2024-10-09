package com.rebelo.springsecurityjwt.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateRequest extends UserRequest {

    @NotBlank(message = "password is mandatory")
    @Size(min = 5, max = 20, message = "password must be between 5 to 20 characters")
    private String password;

    @Override
    public String toString() {
        return "UserCreateRequest{" + super.toString() + ", password='*****'" + '}';
    }
}
