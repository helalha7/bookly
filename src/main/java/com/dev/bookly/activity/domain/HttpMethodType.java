package com.dev.bookly.activity.domain;

public enum HttpMethodType {
    GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS, TRACE;

    public static HttpMethodType fromString(String s) {
        if (s == null) throw new IllegalArgumentException("httpMethod is null");
        return HttpMethodType.valueOf(s.trim().toUpperCase());
    }
}