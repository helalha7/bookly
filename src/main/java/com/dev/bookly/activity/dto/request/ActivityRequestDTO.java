package com.dev.bookly.activity.dto.request;

import com.dev.bookly.activity.domain.ActivityAction;
import com.dev.bookly.activity.domain.HttpMethodType;

public class ActivityRequestDTO {

    private final Long userId;
    private final ActivityAction action;
    private final String endpoint;
    private final HttpMethodType httpMethod;
    private final String details;


    public ActivityRequestDTO(Long userId, ActivityAction action, String endpoint, HttpMethodType httpMethod, String details) {
        this.userId = userId;
        this.action = action;
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
        this.details = details;
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
}
