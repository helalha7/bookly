package com.dev.bookly.scheduling.controllers;

import com.dev.bookly.scheduling.dtos.LocalSlotDTO;
import com.dev.bookly.scheduling.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing availability  resource shifts.
 */

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private AvailabilityService availabilityService;


    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    /**
     * Retrieve all available resource shifts for a given serviceId , resourceId , and date.
     *
     * @param serviceId the ID of the service
     * @param resourceId the ID of the resource
     * @param date the date and time we want to check if available
     * @return list of LocalSlotDTO objects
     */
    @GetMapping("/slots")
    public ResponseEntity<List<LocalSlotDTO>> listSlots(@RequestParam Long serviceId, @RequestParam(required = false) Long resourceId, @RequestParam String date) {
        List<LocalSlotDTO> list = availabilityService.listSlots(serviceId, resourceId, date);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
