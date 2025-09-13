package com.dev.bookly.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/business/{businessId}")
@RestController
public class ServiceCatalogController {
    private ServiceCatalogService svc;

    @Autowired
    public ServiceCatalogController(ServiceCatalogService svc){
        this.svc=svc;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponseDTO>> listResponseEntity(@PathVariable Long businessId){

    }

    @PostMapping("/services")
    public ResponseEntity<List<ServiceResponseDTO>> createService(@PathVariable Long businessId, @RequestBody ServiceRequestDTO dto){

    }

    @PutMapping("/services/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable Long businessId
                                                                    , @PathVariable Long serviceId
                                                                        ,@RequestBody ServiceRequestDTO dto){


    }

    @GetMapping("/services/{serviceId}/resources")
    public ResponseEntity<List<ResourceResponseDTO>> listResources(@PathVariable Long businessId
            , @PathVariable Long serviceId){


    }

    @PostMapping("/services/{serviceId}/resources")
    public ResponseEntity<ResourceResponseDTO> createResources(@PathVariable Long businessId
            , @PathVariable Long serviceId
            ,@RequestBody ResourceRequestDTO dto){


    }

    @PutMapping("/resources/{resourceId}")
    public ResponseEntity<ResourceResponseDTO> updateResource(@PathVariable Long businessId , @PathVariable Long resourceId ,@RequestBody ResourceRequestDTO dto ){

    }


}
