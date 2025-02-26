package com.rebelo.springsecurityjwt.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BusinessConstant {

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ANY_PATH_SUFFIX = "/**";
    public static final String HEALTH_CHECK_PATH = "/health-check";
    public static final String AUTHORIZATION_PATH = "/authorization";
    public static final String SIGN_UP_PATH = "/sign-up";
    public static final String AUTHENTICATE_PATH = "/authenticate";
    public static final String USERS_PATH = "/users";
    public static final String FILTER_PATH = "/filter";

}
