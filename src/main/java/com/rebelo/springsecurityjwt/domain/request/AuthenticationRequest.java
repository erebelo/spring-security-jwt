package com.rebelo.springsecurityjwt.domain.request;

import static com.rebelo.springsecurityjwt.util.MaskUtil.maskEmail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequest{" + "email='" + maskEmail(email) + '\'' + ", password='*****'" + '}';
    }
}
