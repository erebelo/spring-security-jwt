package com.rebelo.springsecurityjwt.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BusinessConstants {

    public static final String AUTHORIZATION_PATH = "/authorization";
    public static final String SIGN_UP_PATH = "/sign-up";
    public static final String AUTHENTICATE_PATH = "/authenticate";

}
