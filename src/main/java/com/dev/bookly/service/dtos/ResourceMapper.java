package com.dev.bookly.service.dtos;

import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;

public class ResourceMapper {
    public static Resource toResource(Long serivceId , ResourceRequestDTO resourceRequestDTO) {
        Resource resource = new Resource(
                null,
                serivceId,
                resourceRequestDTO.getName(),
                resourceRequestDTO.getCapacity()
        );
        return resource;
    }

    public static ResourceResponseDTO toResourceResponseDTO(Resource resource) {
        ResourceResponseDTO  resourceResponseDTO = new ResourceResponseDTO(
                resource.getId(),
                resource.getServiceId(),
                resource.getName(),
                resource.getCapacity()
        );
        return resourceResponseDTO;
    }
}
