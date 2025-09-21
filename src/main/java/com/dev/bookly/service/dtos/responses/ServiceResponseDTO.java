package com.dev.bookly.service.dtos.responses;

public class ServiceResponseDTO {
    private final Long id;
    private final Long businessId;

    private final String name;

    private final String description;

    private final Integer durationMins;
    private final Double price;

    private final Boolean active;

    public ServiceResponseDTO(Long id, Long businessId, String name, String description, Integer durationMins, Double price, Boolean active) {
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

    public Integer getDurationMins() {
        return durationMins;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean isActive() {
        return active;
    }
}
