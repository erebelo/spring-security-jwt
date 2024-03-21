package com.rebelo.springsecurityjwt.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebelo.springsecurityjwt.domain.enumeration.type.EnumCodeValueType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum implements EnumCodeValueType {

    @JsonProperty("User")
    USER("User", "Regular user"),

    @JsonProperty("Admin")
    ADMIN("Admin", "Admin user");

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
