package com.dev.bookly.businessProfile.controllers;

import com.dev.bookly.activity.domain.ActivityAction;
import com.dev.bookly.activity.domain.HttpMethodType;
import com.dev.bookly.activity.dto.request.ActivityRequestDTO;
import com.dev.bookly.activity.service.impl.ActivityServiceImpl;
import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private BusinessServiceImpl businessServiceImpl;
    private ActivityServiceImpl  activityServiceImpl;

    @Autowired
    BusinessController(BusinessServiceImpl businessServiceImpl, ActivityServiceImpl activityServiceImpl) {
        this.businessServiceImpl = businessServiceImpl;
        this.activityServiceImpl = activityServiceImpl;

    }


    // get all businesses
    @GetMapping
    public ResponseEntity<List<BusinessResponseDTO>> getAllBusiness(@AuthenticationPrincipal UserDetails userDetails){

        System.out.println(userDetails.getUsername());
        List<BusinessResponseDTO> businessResponseDTOS = businessServiceImpl.getAll(((UserDetailsImpl) userDetails).getId());

        return new ResponseEntity<>(businessResponseDTOS, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> getBusinessById(@PathVariable Long id){

        BusinessResponseDTO businessResponseDTO = businessServiceImpl.get(id);

        return new ResponseEntity<>(businessResponseDTO, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<BusinessResponseDTO> create(@RequestBody BusinessRequestDTO businessRequestDTO, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request){

        Long userId = ((UserDetailsImpl) userDetails).getId();
        String endpoint = request.getRequestURI();
        HttpMethodType method = HttpMethodType.fromString(request.getMethod());
        try{
            BusinessResponseDTO responseDTO = businessServiceImpl.create(businessRequestDTO, ((UserDetailsImpl) userDetails).getId());
            String details = HelpFunc.toJson(Map.of("status", "SUCCESS"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.CREATE, endpoint, method, details
            ));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (Exception e){
            String details = HelpFunc.toJson(Map.of("status", "FAILED"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.CREATE, endpoint, method, details
            ));
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> update(@PathVariable Long id, @RequestBody BusinessRequestDTO businessRequestDTO, @AuthenticationPrincipal UserDetails userDetails,HttpServletRequest request){
        Long userId = ((UserDetailsImpl) userDetails).getId();
        String endpoint = request.getRequestURI();
        HttpMethodType method = HttpMethodType.fromString(request.getMethod());
        try{
            BusinessResponseDTO responseDTO =  businessServiceImpl.update(id, businessRequestDTO, ((UserDetailsImpl) userDetails).getId());
            String details = HelpFunc.toJson(Map.of("status", "SUCCESS"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.UPDATE, endpoint, method, details
            ));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            String details = HelpFunc.toJson(Map.of("status", "FAILED"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.UPDATE, endpoint, method, details
            ));
            throw e;
        }

    }


    @PutMapping("/{id}/status")
    public ResponseEntity<BusinessResponseDTO> status(@PathVariable Long id,@RequestParam("active")  Boolean active, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request){
        Long userId = ((UserDetailsImpl) userDetails).getId();
        String endpoint = request.getRequestURI();
        HttpMethodType method = HttpMethodType.fromString(request.getMethod());
        try{
            BusinessResponseDTO responseDTO = businessServiceImpl.status(id, active, ((UserDetailsImpl) userDetails).getId());
            String details = HelpFunc.toJson(Map.of("status", "SUCCESS"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.UPDATE, endpoint, method, details
            ));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            String details = HelpFunc.toJson(Map.of("status", "FAILED"));
            activityServiceImpl.log(new ActivityRequestDTO(
                    userId, ActivityAction.UPDATE, endpoint, method, details
            ));
            throw e;
        }

    }



    //this endpoint in admin now, its work here, maybe later we reuse it... don't remove!!
//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable Long id){
//
//         businessServiceImpl.delete(id);
//
//        return "Business with id " + id + " was deleted successfully";
//
//    }



}
