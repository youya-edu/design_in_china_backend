package org.dic.demo.security;

import org.apache.commons.lang3.StringUtils;

public enum AuthenticationType {
    BASIC("Basic"),
    DIGEST("Digest"),
    BEARER("Bearer");

    private final String value;

    AuthenticationType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static boolean isBasic(String authorizationHeader) {
        return is(authorizationHeader, BASIC);
    }

    public static boolean isDigest(String authorizationHeader) {
        return is(authorizationHeader, DIGEST);
    }

    public static boolean isBearer(String authorizationHeader) {
        return is(authorizationHeader, BEARER);
    }

    private static boolean is(String authorizationHeader, AuthenticationType authenticationType) {
        return StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(authenticationType.value());
    }
}
