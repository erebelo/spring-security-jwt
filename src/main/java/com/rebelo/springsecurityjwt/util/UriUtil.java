package com.rebelo.springsecurityjwt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.AUTHORIZATION_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.SIGN_UP_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.USER_PATH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriUtil {

    public static URI buildSignUpUri(Long id) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri().toString();
        uri = uri.replace(AUTHORIZATION_PATH + SIGN_UP_PATH, USER_PATH);

        return URI.create(uri);
    }
}
