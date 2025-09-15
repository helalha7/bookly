package com.dev.bookly.activity.domain;

public enum ActivityAction {
    LOGIN,
    SIGNUP,
    UPDATE_PROFILE,
    LOGOUT,
    PASSWORD_RESET,
    CREATE,
    UPDATE,
    DELETE;

    public static ActivityAction fromString(String s) {
        if (s == null) throw new IllegalArgumentException("action is null");
        return ActivityAction.valueOf(s.trim().toUpperCase());
    }
}