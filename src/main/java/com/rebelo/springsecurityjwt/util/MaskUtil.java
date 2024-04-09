package com.rebelo.springsecurityjwt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaskUtil {

    public static String maskEmail(String email) {
        if (!ObjectUtils.isEmpty(email)) {
            var atIndex = email.indexOf('@');

            if (atIndex > 2) {
                return "***" + email.substring(atIndex - 2);
            }

            return "***" + email.substring(atIndex);
        }

        return null;
    }
}
