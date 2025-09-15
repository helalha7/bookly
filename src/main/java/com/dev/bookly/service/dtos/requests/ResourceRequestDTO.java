package com.dev.bookly.service.dtos.requests;

public class ResourceRequestDTO {
    private String name;
    private Integer capacity;

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
