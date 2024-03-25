package com.rebelo.springsecurityjwt.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BusinessConstants {

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ANY_PATH_SUFFIX = "/**";
    public static final String HEALTH_CHECK_PATH = "/health-check";
    public static final String AUTHORIZATION_PATH = "/authorization";
    public static final String SIGN_UP_PATH = "/sign-up";
    public static final String AUTHENTICATE_PATH = "/authenticate";
    public static final String USER_PATH = "/user";
    public static final String FILTER_PATH = "/filter";

}
