package com.dev.bookly.scheduling.services.impl;

import com.dev.bookly.scheduling.domains.BusinessShift;
import com.dev.bookly.scheduling.domains.ResourceShift;
import com.dev.bookly.scheduling.dtos.BusinessShiftMapper;
import com.dev.bookly.scheduling.dtos.ResourceShiftMapper;
import com.dev.bookly.scheduling.repository.BusinessShiftRepository;
import com.dev.bookly.scheduling.repository.ResourceShiftRepository;
import com.dev.bookly.scheduling.services.ShiftService;
import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.dtos.ResourceShiftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final BusinessShiftRepository businessShiftRepository;
    private final ResourceShiftRepository resourceShiftRepository;

    @Autowired
    public ShiftServiceImpl(BusinessShiftRepository businessShiftRepository , ResourceShiftRepository resourceShiftRepository) {
        this.businessShiftRepository = businessShiftRepository;
        this.resourceShiftRepository = resourceShiftRepository;
    }

    @Override
    public List<BusinessShiftDTO> listBusinessShifts(Long businessId) {
        List<BusinessShift> shiftList = businessShiftRepository.findByBusinessId(businessId);

        List<BusinessShiftDTO> shiftListDTO = new ArrayList<>();
        for(BusinessShift shift : shiftList){
//            shiftListDTO.add(new BusinessShiftDTO(
//                    shift.getSlotNo(),
//                    shift.getDayOfWeek(),
//                    shift.getStartTime(),
//                    shift.getEndTime()
            shiftListDTO.add(BusinessShiftMapper.toBusinessShiftDTO(shift));
        }
        return shiftListDTO;
    }

    @Override
    public BusinessShiftDTO upsertBusinessShift(Long businessId, BusinessShiftDTO dto) {
        BusinessShift shift = BusinessShiftMapper.toBusinessShift(businessId , dto);
        businessShiftRepository.save(shift);
        return BusinessShiftMapper.toBusinessShiftDTO(shift);
    }

    @Override
    public BusinessShiftDTO updateBusinessShift(Long businessId, Long shiftId, BusinessShiftDTO dto) {
        BusinessShift shift = BusinessShiftMapper.toBusinessShift(businessId , dto);
        businessShiftRepository.update(shiftId ,shift);
        return BusinessShiftMapper.toBusinessShiftDTO(shift);
    }

    @Override
    public void deleteBusinessShift(Long businessId, Long shiftId) {
        businessShiftRepository.delete(shiftId ,businessId);
    }


    @Override
    public List<ResourceShiftDTO> listResourceShifts(Long businessId , Long resourceId) {
        List<ResourceShift> list = resourceShiftRepository.findByResourceId(resourceId);
        List<ResourceShiftDTO> ResourceShiftDTOList = new ArrayList<>();
        for(ResourceShift shift : list){
            ResourceShiftDTOList.add(ResourceShiftMapper.toResourceShiftDTO(shift));
        }
        return ResourceShiftDTOList;
    }

    @Override
    public ResourceShiftDTO upsertResourceShift(Long resourceId, ResourceShiftDTO dto) {
        ResourceShift resourceShift = ResourceShiftMapper.toResourceShift(resourceId , dto);
        ResourceShift resourceShift1 = resourceShiftRepository.save(resourceShift);
        ResourceShiftDTO resourceShiftDTO = ResourceShiftMapper.toResourceShiftDTO(resourceShift1);
        return  resourceShiftDTO;
    }

    @Override
    public ResourceShiftDTO updateResourceShift(Long resourceId, Long shiftId, ResourceShiftDTO dto) {
        ResourceShift resourceShift = ResourceShiftMapper.toResourceShift(resourceId , dto);
        ResourceShift resourceShift1 = resourceShiftRepository.update(shiftId , resourceShift);
        ResourceShiftDTO resourceShiftDTO = ResourceShiftMapper.toResourceShiftDTO(resourceShift1);
        return  resourceShiftDTO;
    }

    @Override
    public void deleteResourceShift(Long resourceId, Long shiftId) {
        resourceShiftRepository.delete(resourceId , shiftId);
    }
}
