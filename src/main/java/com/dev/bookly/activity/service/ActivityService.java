package com.dev.bookly.activity.service;

import com.dev.bookly.activity.domain.Activity;
import com.dev.bookly.activity.dto.request.ActivityRequestDTO;
import com.dev.bookly.activity.dto.response.ActivityResponseDTO;

import java.util.List;

public interface ActivityService {


   public  ActivityResponseDTO log(ActivityRequestDTO newActivity);
   public ActivityResponseDTO findById(Long id);
   public List<ActivityResponseDTO> findAll();
   public List<ActivityResponseDTO> findAllByUserId(Long userId);


}
