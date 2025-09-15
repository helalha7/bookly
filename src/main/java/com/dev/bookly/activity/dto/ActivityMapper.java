package com.dev.bookly.activity.dto;

import com.dev.bookly.activity.domain.Activity;
import com.dev.bookly.activity.dto.response.ActivityResponseDTO;

public class ActivityMapper {

    public static ActivityResponseDTO toResponseDTO(Activity entity) {
        return new ActivityResponseDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getAction(),
                entity.getEndpoint(),
                entity.getHttpMethod(),
                entity.getDetails(),
                entity.getCreatedAt()
             );
    }

    public static Activity fromResponseDTO(Activity responseDTO) {
        return new Activity(
                null,
                responseDTO.getUserId(),
                responseDTO.getAction(),
                responseDTO.getEndpoint(),
                responseDTO.getHttpMethod(),
                responseDTO.getDetails(),
                null

        );
    }


}
