package com.dev.bookly.businessProfile.dtos.response;

public class BusinessResponseDTO {

    private final Long id;
    private final String name;
    private final String address;
    private final String logoUrl;
    private final String description;
    private final String timeZone;
    private final boolean active;

    public BusinessResponseDTO(Long id, String name, String address, String logoUrl, String description, String timeZone, boolean active) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.logoUrl = logoUrl;
        this.description = description;
        this.timeZone = timeZone;
        this.active = active;
    }

    public Long getId() {
        return id;
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
}
