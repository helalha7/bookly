package com.dev.bookly.service.servises;

import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;

import java.util.List;


public interface ServiceCatalogService {
    public List<ServiceResponseDTO> listServices(Long businessId);
    public ServiceResponseDTO createService(Long businessId , ServiceRequestDTO dto);
    public ServiceResponseDTO updateService(Long businessId ,Long serviceId, ServiceRequestDTO dto);
    public List<ResourceResponseDTO> listResources(Long businessId , Long serviceId);
    public ResourceResponseDTO createResource(Long businessId , Long serviceId, ResourceRequestDTO dto);
    public ResourceResponseDTO updateResource(Long businessId,Long servicesId, Long resourceId, ResourceRequestDTO resourceRequestDTO);







}
