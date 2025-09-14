package com.dev.bookly.businessProfile.controllers;

import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTOAdmin;
import com.dev.bookly.businessProfile.exceptions.BusinessNotFoundException;
import com.dev.bookly.businessProfile.services.impl.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/businesses/admin")
public class BusinessControllerForAdmin {

    private BusinessServiceImpl businessServiceImpl;

    @Autowired
    BusinessControllerForAdmin(BusinessServiceImpl businessServiceImpl){
        this.businessServiceImpl = businessServiceImpl;
    }


    @GetMapping("/allUsersBusinesses")
    public ResponseEntity<List<BusinessResponseDTOAdmin>> getAllUsersBusinesses(@AuthenticationPrincipal UserDetails userDetails){

        List<BusinessResponseDTOAdmin> businessResponseDTOS = businessServiceImpl.getAllUsersBusinesses();
        if(businessResponseDTOS.isEmpty()){
            throw new BusinessNotFoundException("There are no businesses yet!");
        }
        return new ResponseEntity<>(businessResponseDTOS, HttpStatus.OK);
    }


    @GetMapping("businessByUserId/{userId}")
    public ResponseEntity<BusinessResponseDTOAdmin> getBusinessByUserId(@PathVariable("userId") Long userId){

        BusinessResponseDTOAdmin businessResponseDTO = businessServiceImpl.getBusinessByUserId(userId);

        if(businessResponseDTO == null){
            throw new BusinessNotFoundException("Business not found! or user don't have any business!");
        }

        return new ResponseEntity<>(businessResponseDTO, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        businessServiceImpl.delete(id);
        return "Business with id " + id + " was deleted successfully";

    }}
