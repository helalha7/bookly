package com.dev.bookly.service.domain;

import java.util.Date;

public class Resource {

    private Long id;
    private Long serviceId;
    private String name;
    private Integer capacity;

    public Resource(Long id, Long serviceId, String name, Integer capacity) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.capacity = capacity;
    }

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
