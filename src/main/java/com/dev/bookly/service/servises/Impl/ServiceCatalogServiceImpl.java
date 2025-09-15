package com.dev.bookly.service.servises.Impl;

import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.domain.Service;
import com.dev.bookly.service.dtos.ResourceMapper;
import com.dev.bookly.service.dtos.ServiceMapper;
import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;
import com.dev.bookly.service.repositories.ResourceRepository;
import com.dev.bookly.service.repositories.ServiceRepository;
import com.dev.bookly.service.servises.ServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dev.bookly.service.dtos.ServiceMapper.toServiceDTO;
import static com.dev.bookly.service.dtos.ServiceMapper.toServiceDomain;

@org.springframework.stereotype.Service
@Transactional
public class ServiceCatalogServiceImpl implements ServiceCatalogService {
    private final ServiceRepository serviceRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public ServiceCatalogServiceImpl(ServiceRepository serviceRepository, ResourceRepository resourceRepository){
        this.serviceRepository=serviceRepository;
        this.resourceRepository=resourceRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponseDTO> listServices(Long businessId) {
        List<Service> services = serviceRepository.findByBusiness(businessId);
        List<ServiceResponseDTO> out = new ArrayList<>(services.size());
        for (Service s : services) {
            out.add(toServiceDTO(s));
        }
        return out;
    }

    @Override
    public ServiceResponseDTO createService(Long businessId, ServiceRequestDTO dto) {

        if (serviceRepository.existsByName(businessId, dto.getName())) {
            throw new IllegalStateException("Service name already exists in this business: " + dto.getName());
        }
        Service toSave = toServiceDomain(null, businessId, dto);
        Service saved = serviceRepository.save(toSave);
        return toServiceDTO(saved);
    }

    @Override
    public ServiceResponseDTO updateService(Long businessId, Long serviceId, ServiceRequestDTO dto) {
        Service current = serviceRepository.findById(serviceId);
        if (current == null) {
            throw new IllegalStateException("Service not found: " + serviceId);
        }
        if (!businessId.equals(current.getBusinessId())) {
            throw new IllegalStateException("Service does not belong to business: " + businessId);
        }

        // If name changes, ensure no duplicate for this business
        if (!current.getName().equalsIgnoreCase(dto.getName())
                && serviceRepository.existsByName(businessId, dto.getName())) {
            throw new IllegalStateException("Service name already exists in this business: " + dto.getName());
        }

        current.setName(dto.getName());
        current.setDescription(dto.getDescription());
        current.setDurationMins(dto.getDurationMins());
        current.setPrice(dto.getPrice());
        current.setActive(dto.isActive());

        Service updated = serviceRepository.update(current);
        return toServiceDTO(updated);
    }






//    @Override
//    public List<ResourceResponseDTO> listResources(Long businessId, Long servicesId) {
//        List<Resource> resources = resourceRepository.findByService(servicesId);
//        List<ResourceResponseDTO> resourcesDTO = new ArrayList<>();
//        for (Resource resource : resources) {
//            ResourceResponseDTO resourceResponseDTO = ResourceMapper.toResourceResponseDTO(resource);
//            resourcesDTO.add(resourceResponseDTO);
//        }
//        return resourcesDTO;
//    }
//
//    @Override
//    public ResourceResponseDTO createResource(Long businessId, Long servicesId, ResourceRequestDTO resourceRequestDTO) {
//        Resource resource = ResourceMapper.toResource(servicesId , resourceRequestDTO);
//        Resource resource1 = resourceRepository.save(resource);
//        ResourceResponseDTO  resourceResponseDTO = ResourceMapper.toResourceResponseDTO(resource1);
//        return resourceResponseDTO;
//    }
//
//    @Override
//    public ResourceResponseDTO updateResource(Long businessId,Long servicesId, Long resourceId, ResourceRequestDTO resourceRequestDTO) {
//        Resource resource = ResourceMapper.toResource(servicesId , resourceRequestDTO);
//        Resource resource1 = resourceRepository.update(resourceId ,resource);
//        ResourceResponseDTO  resourceResponseDTO = ResourceMapper.toResourceResponseDTO(resource1);
//        return resourceResponseDTO;
//    }
@Override
public List<ResourceResponseDTO> listResources(Long businessId, Long servicesId, String username) {
    return null;
}

    @Override
    public ResourceResponseDTO createResource(Long businessId, Long servicesId, ResourceRequestDTO resourceRequestDTO, String username) {
        return null;
    }

    @Override
    public ResourceResponseDTO updateResource(Long businessId, Long servicesId, Long resourceId, ResourceRequestDTO resourceRequestDTO, String username) {
        return null;
    }

    @Override
    public ResourceResponseDTO deleteResource(Long businessId, Long servicesId, Long resourceId, String username) {
        return null;
    }
}
