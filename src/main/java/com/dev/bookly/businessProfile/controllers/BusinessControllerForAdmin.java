package com.dev.bookly.businessProfile.controllers;

import com.dev.bookly.activity.domain.ActivityAction;
import com.dev.bookly.activity.domain.HttpMethodType;
import com.dev.bookly.activity.dto.request.ActivityRequestDTO;
import com.dev.bookly.activity.service.impl.ActivityServiceImpl;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTOAdmin;
import com.dev.bookly.businessProfile.exceptions.BusinessNotFoundException;
import com.dev.bookly.businessProfile.services.impl.BusinessServiceImpl;
import com.dev.bookly.security.services.UserDetailsImpl;
import com.dev.bookly.utils.HelpFunc;
import jakarta.servlet.http.HttpServletRequest;
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
    private ActivityServiceImpl activityServiceImpl;
    @Autowired
    BusinessControllerForAdmin(BusinessServiceImpl businessServiceImpl, ActivityServiceImpl activityServiceImpl) {
        this.businessServiceImpl = businessServiceImpl;
        this.activityServiceImpl = activityServiceImpl;
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
    public ResponseEntity<String> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request ){
        Long userId = ((UserDetailsImpl) userDetails).getId();
        String endpoint = request.getRequestURI();
        HttpMethodType method = HttpMethodType.fromString(request.getMethod());
        try{
            businessServiceImpl.delete(id);
            String details = HelpFunc.toJson(Map.of("status", "SUCCESS"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.DELETE, endpoint, method, details
            ));
            return new ResponseEntity<>("Business with id " + id + " was deleted successfully",HttpStatus.OK);
        }catch (Exception e){
            String details = HelpFunc.toJson(Map.of("status", "FAILED"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.DELETE, endpoint, method, details
            ));
            throw e;
        }


    }}
