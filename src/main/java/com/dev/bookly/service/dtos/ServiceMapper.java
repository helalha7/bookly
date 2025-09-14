package com.dev.bookly.service.dtos;

import com.dev.bookly.service.domain.Service;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;

public class ServiceMapper {

    public static Service toServiceDomain(Long id, Long businessId, ServiceRequestDTO dto) {
        Service s = new Service();
        s.setId(id);
        s.setBusinessId(businessId);
        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        s.setDurationMins(dto.getDurationMins());
        s.setPrice(dto.getPrice());
        s.setActive(dto.isActive());
        return s;
    }

    public static ServiceResponseDTO toServiceDTO(Service s) {
        return new ServiceResponseDTO(
                s.getId(),
                s.getBusinessId(),
                s.getName(),
                s.getDescription(),
                s.getDurationMins(),
                s.getPrice(),
                s.isActive()
        );
    }

}
