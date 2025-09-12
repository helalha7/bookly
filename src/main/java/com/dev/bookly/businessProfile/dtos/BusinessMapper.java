package com.dev.bookly.businessProfile.dtos;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;
import com.dev.bookly.role.dtos.RoleMapper;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class BusinessMapper {

           public static BusinessResponseDTO toResponseDTO(Business entity) {
            return new BusinessResponseDTO(
                    entity.getId(),
                    entity.getName(),
                    entity.getAddress(),
                    entity.getLogoUrl(),
                    entity.getDescription(),
                    entity.getTimeZone(),
                    entity.isActive()
            );
        }
    public static Business toBusiness(BusinessRequestDTO entity,Long userId) {
        return new Business(
                null,
                userId,
                entity.getName(),
                entity.getAddress(),
                entity.getLogoUrl(),
                entity.getDescription(),
                entity.getTimeZone(),
                true,
                null,
                null
        );
    }

    public static Business toBusiness(BusinessRequestDTO entity,Long userId,Long businessId) {
        return new Business(
                businessId,
                userId,
                entity.getName(),
                entity.getAddress(),
                entity.getLogoUrl(),
                entity.getDescription(),
                entity.getTimeZone(),
                true,
                null,
                null
        );
    }
}
