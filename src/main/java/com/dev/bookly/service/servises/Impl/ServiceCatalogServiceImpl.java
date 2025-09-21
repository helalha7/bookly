package com.dev.bookly.service.servises.Impl;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PageResponseDTO;
import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.domain.Service;
import com.dev.bookly.service.dtos.ResourceMapper;
import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;
import com.dev.bookly.service.exceptions.resourcesExceptions.DuplicateResourceException;
import com.dev.bookly.service.exceptions.resourcesExceptions.InvalidCapacityException;
import com.dev.bookly.service.exceptions.resourcesExceptions.ResourceNotFoundException;
import com.dev.bookly.service.exceptions.resourcesExceptions.ValidationException;
import com.dev.bookly.service.exceptions.servicesExceptions.DuplicatedServiceException;
import com.dev.bookly.service.exceptions.servicesExceptions.ServiceNotFoundException;
import com.dev.bookly.service.exceptions.servicesExceptions.ServiceValidationException;
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
    public PageResponseDTO<ServiceResponseDTO> listServices(Long businessId, PageRequestDTO pr) {
        PageResult<Service> page = serviceRepository.findByBusiness(businessId,pr.getOffset(), pr.getSize());
        List<ServiceResponseDTO> out = new ArrayList<>();
        for (Service s : page.getRows()) {
            out.add(toServiceDTO(s));
        }
        return new PageResponseDTO<>(
                out,
                pr.getPage(),
                pr.getSize(),
                page.getTotal()
        );
    }

    @Override
    public ServiceResponseDTO createService(Long businessId, ServiceRequestDTO dto) {
        if (dto.getName()==null || dto.getName().isBlank()){
            throw new ServiceValidationException("invalid service name");
        }
        if (dto.getPrice()==null || dto.getPrice()<0){
            throw new ServiceValidationException("invalid price");
        }
        if (dto.getDurationMins()<0){
            throw new ServiceValidationException("invalid duration");
        }

        if (serviceRepository.existsByName(businessId, dto.getName())) {
            throw new DuplicatedServiceException("Service name already exists in this business: " + dto.getName());
        }
        Service toSave = toServiceDomain(null, businessId, dto);
        Service saved = serviceRepository.save(toSave);
        return toServiceDTO(saved);
    }

    @Override
    public ServiceResponseDTO updateService(Long businessId, Long serviceId, ServiceRequestDTO dto) {
        if (dto.getName()==null || dto.getName().isBlank()){
            throw new ServiceValidationException("invalid service name");
        }
        if (dto.getPrice()==null || dto.getPrice()<0){
            throw new ServiceValidationException("invalid price");
        }
        if (dto.getDurationMins()<0){
            throw new ServiceValidationException("invalid duration");
        }
        Service current = serviceRepository.findById(serviceId);
        if (current == null) {
            throw new ServiceNotFoundException("Service not found: " + serviceId);
        }
        if (!businessId.equals(current.getBusinessId())) {
            throw new ServiceNotFoundException("Service does not belong to business: " + businessId);
        }

        // If name changes, ensure no duplicate for this business
        if (!current.getName().equalsIgnoreCase(dto.getName())
                && serviceRepository.existsByName(businessId, dto.getName())) {
            throw new DuplicatedServiceException("Service name already exists in this business: " + dto.getName());
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
    public PageResponseDTO<ResourceResponseDTO> listResources(Long businessId, Long servicesId, String username,PageRequestDTO pr) {

        PageResult<Resource> page = resourceRepository.findByServiceId(servicesId,pr.getOffset(),pr.getSize());

        if (page==null) {
            throw new ResourceNotFoundException("No resources found for serviceId: " + servicesId);
        }

        List<ResourceResponseDTO> resourcesDTO = new ArrayList<>();
        for (Resource resource : page.getRows()) {
            resourcesDTO.add(ResourceMapper.toResourceResponseDTO(resource));
        }

        return new PageResponseDTO<>(
                resourcesDTO,
                pr.getPage(),
                pr.getSize(),
                page.getTotal()

        );
    }

    @Override
    public ResourceResponseDTO createResource(Long businessId, Long servicesId, ResourceRequestDTO resourceRequestDTO, String username) {

        Resource resource = ResourceMapper.toResource(servicesId, resourceRequestDTO);

        if (resourceRequestDTO.getName() == null || resourceRequestDTO.getName().isBlank()) {
            throw new ValidationException("Resource name cannot be empty");
        }

        if (resourceRequestDTO.getCapacity() <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }

        if (resourceRepository.existsByName(resource.getName())) {
            throw new DuplicateResourceException("Resource '" + resource.getName() + "' already exists.");
        }

        Resource saved = resourceRepository.save(resource);
        return ResourceMapper.toResourceResponseDTO(saved);
    }

    @Override
    public ResourceResponseDTO updateResource(Long businessId,Long servicesId, Long resourceId, ResourceRequestDTO resourceRequestDTO, String username) {

        Resource existing = resourceRepository.getResourceById(resourceId);
        if (existing == null) {
            throw new ResourceNotFoundException("Resource with id " + resourceId + " not found");
        }


        if (resourceRequestDTO.getName() != null) {
            if (resourceRequestDTO.getName().isBlank()) {
                throw new ValidationException("Resource name cannot be empty");
            }
            existing.setName(resourceRequestDTO.getName());
        }


        if (resourceRequestDTO.getCapacity() != null) {
            if (resourceRequestDTO.getCapacity() <= 0) {
                throw new InvalidCapacityException("Capacity must be greater than zero");
            }
            existing.setCapacity(resourceRequestDTO.getCapacity());
        }

        Resource updated = resourceRepository.update(resourceId, existing);
        return ResourceMapper.toResourceResponseDTO(updated);
    }

    @Override
    public ResourceResponseDTO deleteResource(Long businessId, Long servicesId, Long resourceId, String username) {

        Resource resource = resourceRepository.getResourceById(resourceId);

        if (resource == null) {
            throw new ResourceNotFoundException("Resource with id " + resourceId + " not found");
        }

        resourceRepository.delete(resourceId);
        return ResourceMapper.toResourceResponseDTO(resource);
    }
}
