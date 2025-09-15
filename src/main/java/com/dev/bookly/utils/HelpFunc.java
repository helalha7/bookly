package com.dev.bookly.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class HelpFunc {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public HelpFunc() {
    }

    public static String toJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            return "{\"status\":\"UNKNOWN\"}";
        }
    }
}
