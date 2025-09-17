package com.dev.bookly.service.dtos.requests;

public class ResourceRequestDTO {
    private final String name;
    private final Integer capacity;

    public ResourceRequestDTO(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
