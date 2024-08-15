package com.nurd.your.places.models;

public enum Role {
    ROLE_ADMIN,
    ROLE_USER;

    public static boolean isValidRole(String value) {
        if (value == null) return false;
        for (Role role : Role.values()) {
            if (value.equals(role.name())) {
                return true;
            }
        }
        return false;
    }
}
