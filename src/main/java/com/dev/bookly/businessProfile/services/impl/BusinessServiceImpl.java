package com.dev.bookly.businessProfile.services.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.dtos.BusinessMapper;
import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
import com.dev.bookly.businessProfile.repostries.BusinessRepository;
import com.dev.bookly.businessProfile.repostries.impl.BusinessRepositoryMysqlImpl;
import com.dev.bookly.businessProfile.services.BusinessService;
import com.dev.bookly.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {


     private BusinessRepository businessRepository;

     @Autowired
     public BusinessServiceImpl(BusinessRepository businessRepository ) {
         this.businessRepository = businessRepository;
     }


//    @Override
//    public BusinessResponseDTO get(Long userId,Long businessId) {
//        Business business = businessRepository.findById(userId,businessId);
//
//        BusinessResponseDTO responseDTO = BusinessMapper.toResponseDTO(business);
//
//        return responseDTO;
//
//    }


    @Override
    public List<BusinessResponseDTO> getAll(Long userId) {

         List<Business> businesses = businessRepository.findAll(userId);
         List<BusinessResponseDTO> businessResponseDTOS = new ArrayList<>();
         for (Business business : businesses) {
            BusinessResponseDTO businessResponseDTO = BusinessMapper.toResponseDTO(business);
            businessResponseDTOS.add(businessResponseDTO);
         }
         return businessResponseDTOS;

    }

    @Override
    public BusinessResponseDTO create(BusinessRequestDTO businessRequestDTO, Long userId) {
        List<Business> businesses = businessRepository.findAll(userId);
        if (!businesses.isEmpty()) {
            throw new IllegalStateException("User already has a business");
        }

        Business toSave = BusinessMapper.toBusiness(businessRequestDTO,userId);

        Business saved = businessRepository.save(toSave);

        BusinessResponseDTO businessResponseDTO = BusinessMapper.toResponseDTO(saved);

        return businessResponseDTO;
    }

    @Override
    public BusinessResponseDTO update(Long id,BusinessRequestDTO businessRequestDTO, Long userId) {
        List<Business> businesses = businessRepository.findAll(userId);
        if (businesses.isEmpty()) {
            throw new IllegalStateException("User Not has a business");
        }
        Business toUpdate = BusinessMapper.toBusiness(businessRequestDTO,userId,id);

        BusinessResponseDTO responseDTO = BusinessMapper.toResponseDTO(businessRepository.update(toUpdate));

        return responseDTO;
    }

    @Override
    public BusinessResponseDTO status(Long id,boolean active, Long userId) {

         Business business = businessRepository.status(id,active,userId);
         BusinessResponseDTO businessResponseDTO = BusinessMapper.toResponseDTO(business);


         return businessResponseDTO;
    }


}
