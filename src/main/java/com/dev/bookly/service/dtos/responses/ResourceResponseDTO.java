package com.dev.bookly.service.dtos.responses;

public class ResourceResponseDTO {
    private Long id;
    private Long serviceId;
    private String name;
    private int capacity;

    public ResourceResponseDTO(Long id, Long serviceId, String name, int capacity) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
