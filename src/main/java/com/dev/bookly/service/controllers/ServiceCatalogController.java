package com.dev.bookly.service.controllers;

import com.dev.bookly.service.dtos.requests.ResourceRequestDTO;
import com.dev.bookly.service.dtos.requests.ServiceRequestDTO;
import com.dev.bookly.service.dtos.responses.ResourceResponseDTO;
import com.dev.bookly.service.dtos.responses.ServiceResponseDTO;
import com.dev.bookly.service.servises.ServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/businesses/{businessId}")
@RestController
public class ServiceCatalogController {
    private ServiceCatalogService svc;

    @Autowired
    public ServiceCatalogController(ServiceCatalogService svc){
        this.svc=svc;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponseDTO>> listServices(@PathVariable Long businessId) {
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

//    @GetMapping("/services/{serviceId}/resources")
//    public ResponseEntity<List<ResourceResponseDTO>> listResources(@PathVariable Long businessId
//            , @PathVariable Long serviceId){
//
//
//    }
//
//    @PostMapping("/services/{serviceId}/resources")
//    public ResponseEntity<ResourceResponseDTO> createResources(@PathVariable Long businessId
//            , @PathVariable Long serviceId
//            ,@RequestBody ResourceRequestDTO dto){
//
//
//    }
//
//    @PutMapping("/resources/{resourceId}")
//    public ResponseEntity<ResourceResponseDTO> updateResource(@PathVariable Long businessId , @PathVariable Long resourceId ,@RequestBody ResourceRequestDTO dto ){
//
//    }

    @GetMapping("/services/{servicesId}/resources")
    public ResponseEntity<List<ResourceResponseDTO>> listResources(@PathVariable Long businessId , @PathVariable Long servicesId) {
        List<ResourceResponseDTO> resources = svc.listResources(businessId , servicesId );
        return new ResponseEntity<>(resources , HttpStatus.OK);
    }

    @PostMapping("/services/{servicesId}/resources")
    public ResponseEntity<ResourceResponseDTO> createResource(@PathVariable Long businessId , @PathVariable Long servicesId , @RequestBody ResourceRequestDTO resourceRequestDTO) {
        ResourceResponseDTO resource = svc.createResource(businessId , servicesId , resourceRequestDTO);
        return new ResponseEntity<>(resource , HttpStatus.CREATED);
    }

    @PutMapping("/services/{servicesId}/resources/{resourceId}")
    public ResponseEntity<ResourceResponseDTO>  updateResource(@PathVariable  Long businessId, @PathVariable Long servicesId, @PathVariable Long resourceId ,  @RequestBody ResourceRequestDTO resourceRequestDTO) {
        ResourceResponseDTO resource = svc.updateResource(businessId , servicesId, resourceId , resourceRequestDTO);
        return new ResponseEntity<>(resource , HttpStatus.OK);
    }


}
