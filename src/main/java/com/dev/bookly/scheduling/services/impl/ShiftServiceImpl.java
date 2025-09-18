package com.dev.bookly.scheduling.services.impl;

import com.dev.bookly.scheduling.domains.BusinessShift;
import com.dev.bookly.scheduling.domains.ResourceShift;
import com.dev.bookly.scheduling.dtos.BusinessShiftMapper;
import com.dev.bookly.scheduling.dtos.ResourceShiftMapper;
import com.dev.bookly.scheduling.exceptions.InvalidShiftTimeException;
import com.dev.bookly.scheduling.exceptions.NotFoundException;
import com.dev.bookly.scheduling.exceptions.OverlappingShiftException;
import com.dev.bookly.scheduling.repository.BusinessShiftRepository;
import com.dev.bookly.scheduling.repository.ScheduleOwnershipRepository;
import com.dev.bookly.scheduling.repository.ResourceShiftRepository;
import com.dev.bookly.scheduling.services.ShiftService;
import com.dev.bookly.scheduling.dtos.BusinessShiftDTO;
import com.dev.bookly.scheduling.dtos.ResourceShiftDTO;
import com.dev.bookly.scheduling.validators.ShiftValidator;
import com.dev.bookly.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final BusinessShiftRepository businessShiftRepository;
    private final ResourceShiftRepository resourceShiftRepository;
    private final ScheduleOwnershipRepository ownershipRepository;

    @Autowired
    public ShiftServiceImpl(BusinessShiftRepository businessShiftRepository , ResourceShiftRepository resourceShiftRepository , ScheduleOwnershipRepository ownershipRepository) {
        this.businessShiftRepository = businessShiftRepository;
        this.resourceShiftRepository = resourceShiftRepository;
        this.ownershipRepository = ownershipRepository;
    }

    private Long getCurrentUserId() {
        UserDetailsImpl currentUser = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.getId();
    }

    @Override
    public List<BusinessShiftDTO> listBusinessShifts(Long businessId) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsBusiness(businessId, userId)) {
            throw new AccessDeniedException("You do not own this business");
        }

        List<BusinessShift> shiftList = businessShiftRepository.findByBusinessId(businessId);

        List<BusinessShiftDTO> shiftListDTO = new ArrayList<>();
        for(BusinessShift shift : shiftList){
            shiftListDTO.add(BusinessShiftMapper.toBusinessShiftDTO(shift));
        }
        return shiftListDTO;
    }

    @Override
    public BusinessShiftDTO upsertBusinessShift(Long businessId, BusinessShiftDTO dto) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsBusiness(businessId, userId)) {
            throw new AccessDeniedException("You do not own this business");
        }

        // validate the DTO
        ShiftValidator.validateBusinessShift(dto);

        boolean existsOverlap = businessShiftRepository.existsOverlap(businessId, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        BusinessShift shift = BusinessShiftMapper.toBusinessShift(businessId , dto);
        businessShiftRepository.save(shift);
        return BusinessShiftMapper.toBusinessShiftDTO(shift);
    }

    @Override
    public BusinessShiftDTO updateBusinessShift(Long businessId, Long shiftId, BusinessShiftDTO dto) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsBusinessShift(businessId, shiftId, userId)) {
            throw new AccessDeniedException("You do not own this business shift");
        }

        // Load existing shift
        BusinessShift existing = businessShiftRepository.findBusinessShiftById(businessId, shiftId)
                .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found"));

        // Merge only provided fields
        if (dto.getSlotNo() != 0) {
            existing.setSlotNo(dto.getSlotNo());
        }
        if (dto.getDayOfWeek() != 0) {
            existing.setDayOfWeek(dto.getDayOfWeek());
        }
        if (dto.getStartTime() != null) {
            existing.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            existing.setEndTime(dto.getEndTime());
        }

        // validate the DTO
        ShiftValidator.validateBusinessShift(dto);

        // Overlap check
        boolean existsOverlap = businessShiftRepository.existsOverlap(
                businessId, existing.getDayOfWeek(), existing.getStartTime(), existing.getEndTime()
        );
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        // Save update
        int rows = businessShiftRepository.update(shiftId, existing);
        if (rows == 0) {
            throw new NotFoundException("Shift with id " + shiftId + " not found");
        }

        return BusinessShiftMapper.toBusinessShiftDTO(existing);
    }

    @Override
    public void deleteBusinessShift(Long businessId, Long shiftId) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsBusinessShift(businessId, shiftId ,userId)) {
            throw new AccessDeniedException("You do not own this business shift");
        }

        int rows = businessShiftRepository.delete(shiftId ,businessId);
        if(rows == 0){
            throw new NotFoundException("Shift with id " + shiftId + " not found");
        }
    }


    @Override
    public List<ResourceShiftDTO> listResourceShifts(Long businessId , Long serviceId , Long resourceId) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsResource(businessId, serviceId , resourceId ,userId)) {
            throw new AccessDeniedException("You do not own this resource");
        }

        List<ResourceShift> list = resourceShiftRepository.findByResourceId(resourceId);
        List<ResourceShiftDTO> ResourceShiftDTOList = new ArrayList<>();
        for(ResourceShift shift : list){
            ResourceShiftDTOList.add(ResourceShiftMapper.toResourceShiftDTO(shift));
        }
        return ResourceShiftDTOList;
    }

    @Override
    public ResourceShiftDTO upsertResourceShift(Long businessId , Long serviceId ,Long resourceId, ResourceShiftDTO dto) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsResource(businessId, serviceId , resourceId ,userId)) {
            throw new AccessDeniedException("You do not own this resource");
        }

        // validate the DTO
        ShiftValidator.validateResourceShift(dto);

        boolean existsOverlap = resourceShiftRepository.existsOverlap(businessId, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        if (dto.getEffectiveFrom() != null && dto.getEffectiveTo() != null) {
            if (dto.getEffectiveFrom().isAfter(dto.getEffectiveTo())) {
                throw new InvalidShiftTimeException("effectiveFrom must be before effectiveTo");
            }
            if (dto.getEffectiveTo().isBefore(LocalDate.now())) {
                throw new InvalidShiftTimeException("Shift effective range has already expired");
            }
        }

        ResourceShift resourceShift = ResourceShiftMapper.toResourceShift(resourceId , dto);
        ResourceShift resourceShift1 = resourceShiftRepository.save(resourceShift);
        ResourceShiftDTO resourceShiftDTO = ResourceShiftMapper.toResourceShiftDTO(resourceShift1);
        return  resourceShiftDTO;
    }

    @Override
    public ResourceShiftDTO updateResourceShift(Long businessId , Long serviceId ,Long resourceId, Long shiftId, ResourceShiftDTO dto) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsResourceShift(businessId, serviceId, resourceId, shiftId, userId)) {
            throw new AccessDeniedException("You do not own this resource shift");
        }

        if (dto.getEffectiveFrom() != null && dto.getEffectiveTo() != null) {
            if (dto.getEffectiveFrom().isAfter(dto.getEffectiveTo())) {
                throw new InvalidShiftTimeException("effectiveFrom must be before effectiveTo");
            }
            if (dto.getEffectiveTo().isBefore(LocalDate.now())) {
                throw new InvalidShiftTimeException("Shift effective range has already expired");
            }
        }

        // Load existing resource shift
        ResourceShift existing = resourceShiftRepository.findResourceShiftById(resourceId, shiftId)
                .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found"));

        // Merge only provided fields
        if (dto.getSlotNo() != 0) {
            existing.setSlotNo(dto.getSlotNo());
        }
        if (dto.getDayOfWeek() != 0) {
            existing.setDayOfWeek(dto.getDayOfWeek());
        }
        if (dto.getStartTime() != null) {
            existing.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            existing.setEndTime(dto.getEndTime());
        }
        if (dto.getEffectiveFrom() != null) {
            existing.setEffectiveFrom(dto.getEffectiveFrom());
        }
        if (dto.getEffectiveTo() != null) {
            existing.setEffectiveTo(dto.getEffectiveTo());
        }

        // validate the DTO
        ShiftValidator.validateResourceShift(dto);

        // Check overlap
        boolean existsOverlap = resourceShiftRepository.existsOverlap(
                resourceId, existing.getDayOfWeek(), existing.getStartTime(), existing.getEndTime()
        );
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        // Save merged update
        int rows = resourceShiftRepository.update(shiftId, existing);
        if (rows == 0) {
            throw new NotFoundException("Shift with id " + shiftId + " not found");
        }

        // Return updated DTO
        return ResourceShiftMapper.toResourceShiftDTO(existing);
    }

    @Override
    public void deleteResourceShift(Long businessId , Long serviceId ,Long resourceId, Long shiftId) {

        Long userId = getCurrentUserId();

        if (!ownershipRepository.userOwnsResourceShift(businessId, serviceId , resourceId , shiftId , userId)) {
            throw new AccessDeniedException("You do not own this resource shift");
        }

       int rows =  resourceShiftRepository.delete(resourceId , shiftId);
        if(rows == 0){
            throw new NotFoundException("Shift with id " + shiftId + " not found");
        }
    }
}
