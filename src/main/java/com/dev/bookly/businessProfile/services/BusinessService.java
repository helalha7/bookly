package com.dev.bookly.businessProfile.services;


import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;

import java.util.List;

public interface BusinessService {

    public BusinessResponseDTO get(Long businessId);
    public List<BusinessResponseDTO> getAll(Long userId);
    public BusinessResponseDTO create(BusinessRequestDTO businessRequestDTO,Long userId);
    public BusinessResponseDTO update(Long id,BusinessRequestDTO businessRequestDTO,Long userId);
    public BusinessResponseDTO status(Long id,boolean active,Long userId);
    public Long delete(Long id);
//    public void deactivate(Long id);

}
