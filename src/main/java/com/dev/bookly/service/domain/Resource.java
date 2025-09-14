package com.dev.bookly.service.domain;

import java.util.Date;

public class Resource {

    private Long id;
    private Long serviceId;
    private String name;
    private int capacity;
    private Date createdAt;

    public Resource(Long id, Long serviceId, String name, int capacity, Date createdAt) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.capacity = capacity;
        this.createdAt = createdAt;
    }
    public Resource() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
