package com.dev.bookly.businessProfile.controllers;

import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
import com.dev.bookly.businessProfile.services.impl.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/businesses")
public class BusinessController {

    private BusinessServiceImpl businessServiceImpl;

    @Autowired
    BusinessController(BusinessServiceImpl businessServiceImpl){
        this.businessServiceImpl = businessServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<BusinessResponseDTO>> getAllBusiness(@AuthenticationPrincipal UserDetails userDetails){
        BusinessResponseDTO businessResponseDTO;
        businessServiceImpl.get(userDetails.getUsername());
)
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> getBusinessById(@PathVariable Long id){

    }

    @PostMapping
    public ResponseEntity<BusinessResponseDTO> create(@RequestBody BusinessRequestDTO businessRequestDTO){


    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> update(@PathVariable Long id, @RequestBody BusinessRequestDTO businessRequestDTO){

    }

    @PostMapping("/{id}/status")
    public ResponseEntity<BusinessResponseDTO> status(@PathVariable Long id,@RequestBody boolean active){

    }


}
