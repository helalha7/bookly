package com.dev.bookly.activity.service.impl;

import com.dev.bookly.activity.domain.Activity;
import com.dev.bookly.activity.dto.ActivityMapper;
import com.dev.bookly.activity.dto.request.ActivityRequestDTO;
import com.dev.bookly.activity.dto.response.ActivityResponseDTO;
import com.dev.bookly.activity.exceptions.ActivityIsNullException;
import com.dev.bookly.activity.exceptions.ActivityNotFoundException;
import com.dev.bookly.activity.repository.ActivityRepository;
import com.dev.bookly.activity.service.ActivityService;
import com.dev.bookly.realtime.WsHub;
import com.dev.bookly.realtime.model.WsChannel;
import com.dev.bookly.realtime.model.WsTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static ActivityRepository activityRepository;
    private final WsHub wsHub;


    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, WsHub wsHub) {
        this.activityRepository = activityRepository;
        this.wsHub = wsHub;
    }


    @Override
    public ActivityResponseDTO log(ActivityRequestDTO newActivity) {
        if (newActivity == null || newActivity.getUserId() == null) {
            throw new ActivityIsNullException("Activity is null");
        }

        Activity activity = activityRepository.log(newActivity);
        ActivityResponseDTO dto = ActivityMapper.toResponseDTO(activity);

        // send the event immediately to the admin
        wsHub.publish(WsChannel.ADMIN_ACTIVITY, WsTypes.ACTIVITY, dto);

        return dto;
    }

    @Override
    public ActivityResponseDTO findById(Long id) {
        try {
            ActivityResponseDTO activityResponseDTO = ActivityMapper.toResponseDTO(activityRepository.findById(id));
            return activityResponseDTO;
        }catch (EmptyResultDataAccessException e){
            throw new ActivityNotFoundException("Activity with id " + id + " is not found");
        }



    }

    @Override
    public List<ActivityResponseDTO> findAll() {

        List<Activity> activities = activityRepository.findAll();
        List<ActivityResponseDTO> activityResponseDTOS = new ArrayList<>();
        for (Activity activity : activities) {
            ActivityResponseDTO activityResponseDTO = ActivityMapper.toResponseDTO(activity);
            activityResponseDTOS.add(activityResponseDTO);
        }
        return activityResponseDTOS;


    }

    @Override
    public List<ActivityResponseDTO> findAllByUserId(Long userId) {
        List<Activity> activities = activityRepository.findAllByUserId(userId);
        List<ActivityResponseDTO> activityResponseDTOS = new ArrayList<>();
        for (Activity activity : activities) {
            ActivityResponseDTO activityResponseDTO = ActivityMapper.toResponseDTO(activity);
            activityResponseDTOS.add(activityResponseDTO);
        }
        return activityResponseDTOS;
    }
}
