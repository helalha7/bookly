package com.dev.bookly.service.dtos.responses;

public class ServiceResponseDTO {
    private final Long id;
    private final Long businessId;

    private final String name;

    private final String description;

    private final int durationMins;
    private final double price;

    private final boolean active;

    public ServiceResponseDTO(Long id, Long businessId, String name, String description, int durationMins, double price, boolean active) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.description = description;
        this.durationMins = durationMins;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationMins() {
        return durationMins;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }
}
