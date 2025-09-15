package com.dev.bookly.service.controllers;

import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;
import com.dev.bookly.service.servises.OwnershipService;
import com.dev.bookly.service.servises.ServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/me/businesses/{businessId}")
@RestController
public class ServiceCatalogController {
    private final ServiceCatalogService svc;
    private final OwnershipService ownershipService;

    @Autowired
    public ServiceCatalogController(ServiceCatalogService svc,OwnershipService ownershipService){
        this.svc=svc;
        this.ownershipService=ownershipService;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponseDTO>> listServices(@PathVariable Long businessId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsBusiness(businessId,userName);
        List<ServiceResponseDTO> list = svc.listServices(businessId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceResponseDTO> createService(@PathVariable Long businessId,
                                                            @RequestBody ServiceRequestDTO dto) {
        ServiceResponseDTO created = svc.createService(businessId, dto);
        return new ResponseEntity<>(created,HttpStatus.CREATED);
    }

    @PutMapping("/services/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable Long businessId,
                                                            @PathVariable Long serviceId,
                                                            @RequestBody ServiceRequestDTO dto) {
        ServiceResponseDTO updated = svc.updateService(businessId, serviceId, dto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }


//    @PutMapping("/resources/{resourceId}")
//    public ResponseEntity<ResourceResponseDTO> updateResource(@PathVariable Long businessId , @PathVariable Long resourceId ,@RequestBody ResourceRequestDTO dto ){
//
//    }

    @GetMapping("/services/{servicesId}/resources")
    public ResponseEntity<List<ResourceResponseDTO>> listResources(@PathVariable Long businessId , @PathVariable Long servicesId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsService(businessId , servicesId ,username);

        List<ResourceResponseDTO> resources = svc.listResources(businessId , servicesId ,username);
        return new ResponseEntity<>(resources , HttpStatus.OK);
    }

    @PostMapping("/services/{servicesId}/resources")
    public ResponseEntity<ResourceResponseDTO> createResource(@PathVariable Long businessId , @PathVariable Long servicesId , @RequestBody ResourceRequestDTO resourceRequestDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsService(businessId , servicesId ,username);

        ResourceResponseDTO resource = svc.createResource(businessId , servicesId , resourceRequestDTO, username);
        return new ResponseEntity<>(resource , HttpStatus.CREATED);
    }

    @PutMapping("/services/{servicesId}/resources/{resourceId}")
    public ResponseEntity<ResourceResponseDTO>  updateResource(@PathVariable  Long businessId, @PathVariable Long servicesId, @PathVariable Long resourceId ,  @RequestBody ResourceRequestDTO resourceRequestDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsResource(businessId , servicesId , resourceId , username);

        ResourceResponseDTO resource = svc.updateResource(businessId , servicesId, resourceId , resourceRequestDTO , username);
        return new ResponseEntity<>(resource , HttpStatus.OK);
    }

    @DeleteMapping("/services/{servicesId}/resources/{resourceId}")
    public ResponseEntity<ResourceResponseDTO> deleteResource(@PathVariable  Long businessId, @PathVariable Long servicesId, @PathVariable Long resourceId){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsResource(businessId , servicesId , resourceId , username);

        ResourceResponseDTO resource = svc.deleteResource(businessId , servicesId, resourceId , username);
        return new ResponseEntity<>(resource , HttpStatus.OK);
    }


}
