package com.rebelo.springsecurityjwt.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static com.rebelo.springsecurityjwt.util.MaskUtil.maskEmail;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @NotBlank(message = "name is mandatory")
    @Size(min = 2, max = 50, message = "name must be between 6 to 50 characters")
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email(message = "Invalid email address")
    @Size(min = 6, max = 50, message = "email must be between 6 to 50 characters")
    private String email;

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", email='" + maskEmail(email) + '\'' +
                '}';
    }
}
