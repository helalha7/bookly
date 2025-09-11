package com.dev.bookly.businessProfile.services.impl;

import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
import com.dev.bookly.businessProfile.repostries.impl.BusinessRepositoryMysqlImpl;
import com.dev.bookly.businessProfile.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {


     private BusinessRepositoryMysqlImpl businessRepositoryMysqlImpl;

     @Autowired
     public BusinessServiceImpl(BusinessRepositoryMysqlImpl businessRepositoryMysqlImpl) {
         this.businessRepositoryMysqlImpl = businessRepositoryMysqlImpl;
     }

    @Override
    public BusinessResponseDTO get(Long id) {
        return null;
    }

    @Override
    public BusinessResponseDTO create(BusinessRequestDTO businessRequestDTO) {
        return null;
    }

    @Override
    public BusinessResponseDTO update(BusinessRequestDTO businessRequestDTO) {
        return null;
    }

    @Override
    public void activate(Long id) {

    }

    @Override
    public void deactivate(Long id) {

    }
}
