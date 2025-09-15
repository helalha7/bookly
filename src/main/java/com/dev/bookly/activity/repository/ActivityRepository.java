package com.dev.bookly.activity.repository;

import com.dev.bookly.activity.domain.Activity;
import com.dev.bookly.activity.dto.request.ActivityRequestDTO;

import java.util.List;

public interface ActivityRepository {

    public Activity log(ActivityRequestDTO newActivity);
    public Activity findById(Long id);
    public List<Activity> findAll();
    public List<Activity> findAllByUserId(Long userId);

}
