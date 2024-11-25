package com.rebelo.springsecurityjwt.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthorizationUtil {

    public static String getAuthenticatedUsername() {
        var authentication = getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }

        throw new AuthenticationCredentialsNotFoundException("No authenticated user found by security context");

    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
