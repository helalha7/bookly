package com.dev.bookly.businessProfile.services;


import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;

public interface BusinessService {

    public BusinessResponseDTO get(Long id);
    public BusinessResponseDTO create(BusinessRequestDTO businessRequestDTO);
    public BusinessResponseDTO update(BusinessRequestDTO businessRequestDTO);
    public void activate(Long id);
    public void deactivate(Long id);

}
