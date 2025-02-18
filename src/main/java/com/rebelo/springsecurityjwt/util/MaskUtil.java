package com.rebelo.springsecurityjwt.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

@UtilityClass
public class MaskUtil {

    public static String maskEmail(String email) {
        if (!ObjectUtils.isEmpty(email)) {
            int atIndex = email.indexOf('@');

            if (atIndex > 2) {
                return "***" + email.substring(atIndex - 2);
            }

            return "***" + email.substring(atIndex);
        }

        return null;
    }
}
