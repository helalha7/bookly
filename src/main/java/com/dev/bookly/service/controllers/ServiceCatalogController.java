package com.dev.bookly.service.controllers;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PageResponseDTO;
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
    public ResponseEntity<PageResponseDTO<ServiceResponseDTO>> listServices(@PathVariable Long businessId, @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "20") int size) {


        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsBusiness(businessId,userName);

        PageRequestDTO pr = new PageRequestDTO(page, size);
        PageResponseDTO<ServiceResponseDTO> list = svc.listServices(businessId,pr);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceResponseDTO> createService(@PathVariable Long businessId,
                                                            @RequestBody ServiceRequestDTO dto) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsBusiness(businessId,userName);

        ServiceResponseDTO created = svc.createService(businessId, dto);
        return new ResponseEntity<>(created,HttpStatus.CREATED);
    }

    @PutMapping("/services/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable Long businessId,
                                                            @PathVariable Long serviceId,
                                                            @RequestBody ServiceRequestDTO dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsService(businessId , serviceId ,username);

        ServiceResponseDTO updated = svc.updateService(businessId, serviceId, dto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }


//    @PutMapping("/resources/{resourceId}")
//    public ResponseEntity<ResourceResponseDTO> updateResource(@PathVariable Long businessId , @PathVariable Long resourceId ,@RequestBody ResourceRequestDTO dto ){
//
//    }

    @GetMapping("/services/{servicesId}/resources")
    public ResponseEntity<PageResponseDTO<ResourceResponseDTO>> listResources(@PathVariable Long businessId , @PathVariable Long servicesId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownershipService.userOwnsService(businessId , servicesId ,username);
        PageRequestDTO pr = new PageRequestDTO(page, size);

        PageResponseDTO<ResourceResponseDTO> resources = svc.listResources(businessId , servicesId ,username,pr);
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
