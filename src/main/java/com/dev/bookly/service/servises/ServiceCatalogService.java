package com.dev.bookly.service.servises;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PageResponseDTO;
import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ServiceCatalogService {

    @Transactional(readOnly = true)
    public PageResponseDTO<ServiceResponseDTO> listServices(Long businessId, PageRequestDTO pr);

    public ServiceResponseDTO createService(Long businessId , ServiceRequestDTO dto);
    public ServiceResponseDTO updateService(Long businessId ,Long serviceId, ServiceRequestDTO dto);

    public PageResponseDTO<ResourceResponseDTO> listResources(Long businessId, Long servicesId, String username, PageRequestDTO pr);

    public ResourceResponseDTO createResource(Long businessId, Long servicesId, ResourceRequestDTO resourceRequestDTO, String username);

    public ResourceResponseDTO updateResource(Long businessId, Long servicesId, Long resourceId, ResourceRequestDTO resourceRequestDTO, String username);

    public ResourceResponseDTO deleteResource(Long businessId, Long servicesId, Long resourceId, String username);







}
