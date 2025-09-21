package com.dev.bookly.service.dtos.requests;

public class ServiceRequestDTO {

    private final String name;
    private final String description;

    private final Integer durationMins;

    private final Double price;

    private final Boolean active;

    public ServiceRequestDTO(String name, String description, Integer durationMins, Double price, Boolean active) {
        this.name = name;
        this.description = description;
        this.durationMins = durationMins;
        this.price = price;
        this.active = active;
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
