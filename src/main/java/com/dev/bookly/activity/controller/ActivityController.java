package com.dev.bookly.activity.controller;


import com.dev.bookly.activity.domain.Activity;
import com.dev.bookly.activity.dto.response.ActivityResponseDTO;
import com.dev.bookly.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("")
    public ResponseEntity<List<ActivityResponseDTO>> getAllActivities(){
        List<ActivityResponseDTO> activitiesResponse = activityService.findAll();
        return new ResponseEntity<>(activitiesResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> getActivityById(@PathVariable Long id){
        ActivityResponseDTO activityResponseDTO = activityService.findById(id);
        return new ResponseEntity<>(activityResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/userActivity/{userId}")
    public ResponseEntity<Map<Long, List<ActivityResponseDTO>>> getActivitiesByUserId(@PathVariable Long userId) {
        List<ActivityResponseDTO> activities = activityService.findAllByUserId(userId);
        return ResponseEntity.ok(Map.of(userId, activities));
    }

}
