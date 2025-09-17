package com.dev.bookly.service.dtos.requests;

public class ServiceRequestDTO {

    private final String name;
    private final String description;

    private final int durationMins;

    private final double price;

    private final boolean active;

    public ServiceRequestDTO(String name, String description, int durationMins, double price, boolean active) {
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
