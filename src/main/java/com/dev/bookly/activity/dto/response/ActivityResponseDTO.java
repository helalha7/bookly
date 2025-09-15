package com.dev.bookly.activity.dto.response;

import com.dev.bookly.activity.domain.ActivityAction;
import com.dev.bookly.activity.domain.HttpMethodType;

import java.time.Instant;
import java.util.Date;

public class ActivityResponseDTO {
    private Long id;
    private final Long userId;
    private final ActivityAction action;
    private final String endpoint;
    private final HttpMethodType httpMethod;
    private final String details;
    private final Instant createdAt;

    public ActivityResponseDTO(Long id, Long userId, ActivityAction action, String endpoint, HttpMethodType httpMethod, String details, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public ActivityAction getAction() {
        return action;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public HttpMethodType getHttpMethod() {
        return httpMethod;
    }

    public String getDetails() {
        return details;
    }
    public Long getId() {
        return id;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
}
