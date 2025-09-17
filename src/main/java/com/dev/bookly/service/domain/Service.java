package com.dev.bookly.service.domain;

import java.util.Date;

public class Service {
    private Long id;
    private Long businessId;
    private String name;
    private String description;
    private int durationMins;
    private double price;
    private boolean active;
    private Date createdAt;

    public Service(Long id, Long businessId, String name, String description, int durationMins, double price, boolean active, Date createdAt) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.description = description;
        this.durationMins = durationMins;
        this.price = price;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Service() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMins() {
        return durationMins;
    }

    public void setDurationMins(int durationMins) {
        this.durationMins = durationMins;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
