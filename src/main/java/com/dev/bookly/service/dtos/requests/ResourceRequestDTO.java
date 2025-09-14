package com.dev.bookly.service.dtos.requests;

public class ResourceRequestDTO {
    private final String name;
    private final int capacity;

    public ResourceRequestDTO(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
