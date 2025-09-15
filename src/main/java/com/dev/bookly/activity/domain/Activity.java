package com.dev.bookly.activity.domain;

import com.dev.bookly.activity.dto.response.ActivityResponseDTO;

import java.time.Instant;
import java.util.Objects;


public final class Activity {
    private final Long id;
    private final Long userId;
    private final ActivityAction action;
    private final String endpoint;
    private final HttpMethodType httpMethod;
    private final String details;
    private final Instant createdAt;

    public Activity(Long id, Long userId, ActivityAction action,
                    String endpoint, HttpMethodType httpMethod,
                    String details, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.action = Objects.requireNonNull(action, "action");
        this.endpoint = Objects.requireNonNull(endpoint, "endpoint");
        this.httpMethod = Objects.requireNonNull(httpMethod, "httpMethod");
        this.details = details;
        this.createdAt = createdAt;
    }

//    public static Activity newUnpersisted(Long userId, ActivityAction action,
//                                          String endpoint, HttpMethodType method,
//                                           String details) {
//        return new Activity(null, userId, action, endpoint, method, details, null);
//    }
//
//    public static Activity persisted(long id, Long userId, ActivityAction action,
//                                     String endpoint, HttpMethodType method,
//                                      String details, Instant createdAt) {
//        return new Activity(id, userId, action, endpoint, method, details, createdAt);
//    }



    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public ActivityAction getAction() { return action; }
    public String getEndpoint() { return endpoint; }
    public HttpMethodType getHttpMethod() { return httpMethod; }
    public String getDetails() { return details; }
    public Instant getCreatedAt() { return createdAt; }
}