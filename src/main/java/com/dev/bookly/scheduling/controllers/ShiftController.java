package com.dev.bookly.scheduling.controllers;


import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses/{businessId}/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping()
    public ResponseEntity<List<BusinessShiftDTO>> listBusinessShifts(@PathVariable Long businessId) {
        List<BusinessShiftDTO> list = shiftService.listBusinessShifts(businessId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BusinessShiftDTO> upsertBusinessShift(@PathVariable Long businessId ,@RequestBody BusinessShiftDTO businessShiftDTO) {
        BusinessShiftDTO shiftDTO = shiftService.upsertBusinessShift(businessId , businessShiftDTO);
        return new ResponseEntity<>(shiftDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{shiftId}")
    public ResponseEntity<BusinessShiftDTO> updateBusinessShift(@PathVariable Long businessId , @PathVariable Long shiftId, @RequestBody BusinessShiftDTO businessShiftDTO){
        BusinessShiftDTO shiftDTO = shiftService.updateBusinessShift(businessId , shiftId , businessShiftDTO);
        return new ResponseEntity<>(shiftDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteBusinessShift(@PathVariable Long businessId , @PathVariable Long shiftId) {
        shiftService.deleteBusinessShift(businessId, shiftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
