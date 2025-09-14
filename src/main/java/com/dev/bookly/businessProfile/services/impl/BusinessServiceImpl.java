package com.dev.bookly.businessProfile.services.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.dtos.BusinessMapper;
import com.dev.bookly.businessProfile.dtos.request.BusinessRequestDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTOAdmin;
import com.dev.bookly.businessProfile.exceptions.BusinessAlreadyExistsException;
import com.dev.bookly.businessProfile.exceptions.BusinessEmptyException;
import com.dev.bookly.businessProfile.exceptions.BusinessNotFoundException;
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


    @Override
    public BusinessResponseDTO get(Long businessId) {
        Business business = businessRepository.findById(businessId);

        BusinessResponseDTO responseDTO = BusinessMapper.toResponseDTO(business);

        return responseDTO;

    }


    @Override
    public List<BusinessResponseDTO> getAll(Long userId) {

         List<Business> businesses = businessRepository.findAll(userId);
         List<BusinessResponseDTO> businessResponseDTOS = new ArrayList<>();
         for (Business business : businesses) {
            BusinessResponseDTO businessResponseDTO = BusinessMapper.toResponseDTO(business);
            businessResponseDTOS.add(businessResponseDTO);
         }
         if (businessResponseDTOS.isEmpty()) {
             throw new BusinessEmptyException("the user with id " + userId + " don't have any businesses");
         }
         return businessResponseDTOS;

    }

    @Override
    public BusinessResponseDTO create(BusinessRequestDTO businessRequestDTO, Long userId) {
        List<Business> businesses = businessRepository.findAll(userId);
        if (!businesses.isEmpty()) {
            throw new BusinessAlreadyExistsException("User already has a business with name: " + businessRequestDTO.getName());
        }

        Business toSave = BusinessMapper.toBusiness(businessRequestDTO,userId);

        Business saved = businessRepository.save(toSave);

        BusinessResponseDTO businessResponseDTO = BusinessMapper.toResponseDTO(saved);

        return businessResponseDTO;
    }

    @Override
    public BusinessResponseDTO update(Long id,BusinessRequestDTO businessRequestDTO, Long userId) {
       Business businesses = businessRepository.findById(id);
        if (businesses == null) {
            throw new BusinessNotFoundException("There is no business to user with id: " + id );
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

    @Override
    public Long delete(Long id) {
         Business business = businessRepository.findById(id);
         if(business==null){
             throw new BusinessNotFoundException("There is no business with id: " + id);
         }
         Long toDelete = businessRepository.delete(id);
        return toDelete;
    }

    @Override
    public List<BusinessResponseDTOAdmin> getAllUsersBusinesses() {
         List<BusinessResponseDTOAdmin>  businessResponseDTOS = new ArrayList<>();
         List<Business> businesses = businessRepository.getAllUsersBusinesses();
         for (Business business : businesses) {
             BusinessResponseDTOAdmin businessResponseDTO = BusinessMapper.toResponseDTOAdmin(business);
             businessResponseDTOS.add(businessResponseDTO);
         }
        return businessResponseDTOS;
    }

    @Override
    public BusinessResponseDTOAdmin getBusinessByUserId(Long userId) {

         BusinessResponseDTOAdmin businessResponseDTOAdmin = BusinessMapper.toResponseDTOAdmin(businessRepository.getBusinessByUserId(userId));

        return businessResponseDTOAdmin;
    }


}
