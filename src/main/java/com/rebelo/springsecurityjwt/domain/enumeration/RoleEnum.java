package com.rebelo.springsecurityjwt.domain.enumeration;

import com.rebelo.springsecurityjwt.domain.enumeration.type.EnumCodeValueType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum implements EnumCodeValueType {

    USER("USER", "Regular user"), ADMIN("ADMIN", "Admin user");

    private static final Map<String, RoleEnum> ENUM_MAP;

    static {
        Map<String, RoleEnum> map = new HashMap<>();
        for (RoleEnum instance : RoleEnum.values()) {
            map.put(instance.getCode(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    private final String code;
    private final String value;

    public static RoleEnum fromCode(String code) {
        return ENUM_MAP.get(code);
    }
}
