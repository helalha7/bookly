package com.dev.bookly.scheduling.controllers;


import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.dtos.ResourceShiftDTO;
import com.dev.bookly.scheduling.services.ShiftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * REST controller for managing business and resource shifts.
 * Provides CRUD operations for both business-level shifts and resource-level shifts.
 */

@RestController
@RequestMapping("/api/businesses/{businessId}")
public class ShiftController {

    private final ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    /**
     * Retrieve all business shifts for a given business.
     *
     * @param businessId the ID of the business
     * @return list of BusinessShiftDTO objects
     */
    @GetMapping("/shifts")
    public ResponseEntity<List<BusinessShiftDTO>> listBusinessShifts(@PathVariable Long businessId) {
        List<BusinessShiftDTO> list = shiftService.listBusinessShifts(businessId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Create a new shift or update an existing one for a business.
     *
     * @param businessId       the ID of the business
     * @param businessShiftDTO the shift data transfer object
     * @return the saved BusinessShiftDTO
     */
    @PostMapping("/shifts")
    public ResponseEntity<BusinessShiftDTO> upsertBusinessShift(@PathVariable Long businessId ,@Valid @RequestBody BusinessShiftDTO businessShiftDTO) {
        BusinessShiftDTO shiftDTO = shiftService.upsertBusinessShift(businessId , businessShiftDTO);
        return new ResponseEntity<>(shiftDTO, HttpStatus.CREATED);
    }

    /**
     * Update an existing business shift by ID.
     *
     * @param businessId       the ID of the business
     * @param shiftId          the ID of the shift to update
     * @param businessShiftDTO the updated shift data
     * @return the updated BusinessShiftDTO
     */
    @PutMapping("/shifts/{shiftId}")
    public ResponseEntity<BusinessShiftDTO> updateBusinessShift(@PathVariable Long businessId ,
                                                                @PathVariable Long shiftId, @Valid @RequestBody BusinessShiftDTO businessShiftDTO){
        BusinessShiftDTO shiftDTO = shiftService.updateBusinessShift(businessId , shiftId , businessShiftDTO);
        return new ResponseEntity<>(shiftDTO, HttpStatus.OK);
    }

    /**
     * Delete a business shift by ID.
     *
     * @param businessId the ID of the business
     * @param shiftId    the ID of the shift to delete
     * @return HTTP 200 if successful
     */
    @DeleteMapping("/shifts/{shiftId}")
    public ResponseEntity<Void> deleteBusinessShift(@PathVariable Long businessId , @PathVariable Long shiftId) {
        shiftService.deleteBusinessShift(businessId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieve all resource shifts for a given resource under a service and business.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @return list of ResourceShiftDTO objects
     */
    @GetMapping("/services/{serviceId}/resources/{resourceId}/shifts")
    public ResponseEntity<List<ResourceShiftDTO>> listResourcesShifts(@PathVariable Long businessId, @PathVariable Long serviceId,@PathVariable Long resourceId) {
        List<ResourceShiftDTO> list = shiftService.listResourceShifts(businessId , serviceId , resourceId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Create or update a shift for a resource under a specific business and service.
     *
     * @param businessId       the ID of the business
     * @param serviceId        the ID of the service
     * @param resourceId       the ID of the resource
     * @param resourceShiftDTO the shift data transfer object
     * @return the saved ResourceShiftDTO
     */
    @PostMapping("/services/{serviceId}/resources/{resourceId}/shifts")
    public ResponseEntity<ResourceShiftDTO> upsertResourceShift(@PathVariable Long businessId , @PathVariable Long serviceId ,
                                                                @PathVariable Long resourceId, @Valid @RequestBody ResourceShiftDTO resourceShiftDTO) {
        ResourceShiftDTO dto = shiftService.upsertResourceShift(businessId , serviceId , resourceId , resourceShiftDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Update an existing resource shift by ID.
     *
     * @param businessId       the ID of the business
     * @param serviceId        the ID of the service
     * @param resourceId       the ID of the resource
     * @param shiftId          the ID of the shift to update
     * @param resourceShiftDTO the updated shift data
     * @return the updated ResourceShiftDTO
     */
    @PutMapping("/services/{serviceId}/resources/{resourceId}/shifts/{shiftId}")
    public ResponseEntity<ResourceShiftDTO> updateResourceShift(@PathVariable Long businessId , @PathVariable Long serviceId ,@PathVariable Long resourceId,
                                                                @PathVariable Long shiftId , @Valid @RequestBody ResourceShiftDTO resourceShiftDTO ){
        ResourceShiftDTO dto = shiftService.updateResourceShift(businessId , serviceId , resourceId , shiftId,resourceShiftDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Delete a resource shift by ID.
     *
     * @param businessId the ID of the business
     * @param serviceId  the ID of the service
     * @param resourceId the ID of the resource
     * @param shiftId    the ID of the shift to delete
     * @return HTTP 200 if successful
     */
    @DeleteMapping("/services/{serviceId}/resources/{resourceId}/shifts/{shiftId}")
    public ResponseEntity<Void> deleteResourceShift(@PathVariable Long businessId , @PathVariable Long serviceId ,
                                                    @PathVariable Long resourceId , @PathVariable Long shiftId) {
        shiftService.deleteResourceShift(businessId , serviceId , resourceId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
