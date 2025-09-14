package com.dev.bookly.businessProfile.domains;


import java.time.Instant;
import java.util.Date;

public class Business {

    private Long id;
    private Long userId;
    private String name;
    private String address;
    private String logoUrl;
    private String description;
    private String timeZone;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;

    public Business(Long id, Long userId, String name, String address, String logoUrl, String description, String timeZone, boolean active, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.logoUrl = logoUrl;
        this.description = description;
        this.timeZone = timeZone;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public boolean isActive() {
        return active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
