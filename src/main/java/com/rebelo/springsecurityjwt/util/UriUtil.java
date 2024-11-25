package com.rebelo.springsecurityjwt.util;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.AUTHORIZATION_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.SIGN_UP_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.USERS_PATH;

import java.net.URI;
import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class UriUtil {

    public static URI buildSignUpUri(Long id) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri().toString();
        uri = uri.replace(AUTHORIZATION_PATH + SIGN_UP_PATH, USERS_PATH);

        return URI.create(uri);
    }
}
