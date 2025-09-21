package com.dev.bookly.scheduling.services.impl;

import com.dev.bookly.scheduling.domains.BusinessShift;
import com.dev.bookly.scheduling.domains.ResourceShift;
import com.dev.bookly.scheduling.dtos.BusinessShiftMapper;
import com.dev.bookly.scheduling.dtos.ResourceShiftMapper;
import com.dev.bookly.scheduling.exceptions.DuplicateShiftException;
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
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private boolean isAdmin() {
        UserDetailsImpl currentUser = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.isAdmin();
    }

    @Override
    public List<BusinessShiftDTO> listBusinessShifts(Long businessId) {

        Long userId = getCurrentUserId();

        if (!isAdmin() && !ownershipRepository.userOwnsBusiness(businessId, userId)) {
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
    @Transactional
    public BusinessShiftDTO upsertBusinessShift(Long businessId, BusinessShiftDTO dto) {

        Long userId = getCurrentUserId();

        if (!isAdmin() && !ownershipRepository.userOwnsBusiness(businessId, userId)) {
            throw new AccessDeniedException("You do not own this business");
        }

        // validate the DTO
        ShiftValidator.validateBusinessShift(dto);

        boolean existsOverlap = businessShiftRepository.existsOverlap(businessId, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        BusinessShift shift = BusinessShiftMapper.toBusinessShift(businessId , dto);

        try {
            BusinessShift saved = businessShiftRepository.save(shift)
                    .orElseThrow(() -> new NotFoundException("Failed to save business shift"));
            return BusinessShiftMapper.toBusinessShiftDTO(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateShiftException("Shift with the same slot number already exists");
        }
    }

    @Override
    @Transactional
    public BusinessShiftDTO updateBusinessShift(Long businessId, Long shiftId, BusinessShiftDTO dto) {

        Long userId = getCurrentUserId();


        // Load existing shift
        BusinessShift existing = businessShiftRepository.findBusinessShiftById(businessId, shiftId)
                .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found"));

        if (!isAdmin() && !ownershipRepository.userOwnsBusinessShift(businessId, shiftId, userId)) {
            throw new AccessDeniedException("You do not own this business shift");
        }

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
        boolean existsOverlap = businessShiftRepository.existsOverlapExcludingId(
                shiftId , businessId, existing.getDayOfWeek(), existing.getStartTime(), existing.getEndTime()
        );
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        // Save update
        try {
            int rows = businessShiftRepository.update(shiftId, existing);
            if (rows == 0) {
                throw new NotFoundException("Shift with id " + shiftId + " not found");
            }

            BusinessShift updated = businessShiftRepository.findBusinessShiftById(businessId, shiftId)
                    .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found after update"));

            return BusinessShiftMapper.toBusinessShiftDTO(updated);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateShiftException("Another shift with the same slot number already exists for this business");
        }
    }

    @Override
    @Transactional
    public void deleteBusinessShift(Long businessId, Long shiftId) {
        Long userId = getCurrentUserId();

        // 1. Check existence
        BusinessShift existing = businessShiftRepository.findBusinessShiftById(businessId , shiftId)
                .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found"));

        // 2. Check ownership
        if (!isAdmin() && !ownershipRepository.userOwnsBusinessShift(businessId, shiftId, userId)) {
            throw new AccessDeniedException("You do not own this shift");
        }

        // 3. Delete
        int rows = businessShiftRepository.delete(shiftId , businessId);
        if(rows == 0 ){
            throw new NotFoundException("Shift with id " + shiftId + " not found");
        }
    }


    @Override
    public List<ResourceShiftDTO> listResourceShifts(Long businessId , Long serviceId , Long resourceId) {

        Long userId = getCurrentUserId();

        if (!isAdmin() && !ownershipRepository.userOwnsResource(businessId, serviceId , resourceId ,userId)) {
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
    @Transactional
    public ResourceShiftDTO upsertResourceShift(Long businessId , Long serviceId ,Long resourceId, ResourceShiftDTO dto) {

        Long userId = getCurrentUserId();

        if (!isAdmin() && !ownershipRepository.userOwnsResource(businessId, serviceId , resourceId ,userId)) {
            throw new AccessDeniedException("You do not own this resource");
        }

        // validate the DTO
        ShiftValidator.validateResourceShift(dto);

        boolean existsOverlap = resourceShiftRepository.existsOverlap(resourceId, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime());
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

        // Ensure resource shift fits inside business shift
        BusinessShift businessShift = businessShiftRepository
                .findByBusinessIdAndDayAndSlot(businessId, dto.getDayOfWeek(), dto.getSlotNo())
                .orElseThrow(() -> new InvalidShiftTimeException("No matching business shift for this resource shift"));

        if (dto.getStartTime().isBefore(businessShift.getStartTime()) ||
                dto.getEndTime().isAfter(businessShift.getEndTime())) {
            throw new InvalidShiftTimeException("Resource shift must fit within business shift hours");
        }

        ResourceShift resourceShift = ResourceShiftMapper.toResourceShift(resourceId , dto);

        try {
            ResourceShift saved = resourceShiftRepository.save(resourceShift)
                    .orElseThrow(() -> new NotFoundException("Failed to save resource shift"));
            return ResourceShiftMapper.toResourceShiftDTO(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateShiftException("Shift with the same slot number already exists for this resource");
        }
    }

    @Override
    @Transactional
    public ResourceShiftDTO updateResourceShift(Long businessId , Long serviceId ,Long resourceId, Long shiftId, ResourceShiftDTO dto) {

        // Load existing resource shift
        ResourceShift existing = resourceShiftRepository.findResourceShiftById(resourceId, shiftId)
                .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found"));

        Long userId = getCurrentUserId();

        if (!isAdmin() && !ownershipRepository.userOwnsResourceShift(businessId, serviceId, resourceId, shiftId, userId)) {
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
        boolean existsOverlap = resourceShiftRepository.existsOverlapExcludingId(
                shiftId , resourceId, existing.getDayOfWeek(), existing.getStartTime(), existing.getEndTime()
        );
        if (existsOverlap) {
            throw new OverlappingShiftException("Shift overlaps with an existing one");
        }

        // Ensure resource shift fits inside business shift
        BusinessShift businessShift = businessShiftRepository
                .findByBusinessIdAndDayAndSlot(businessId, dto.getDayOfWeek(), dto.getSlotNo())
                .orElseThrow(() -> new InvalidShiftTimeException("No matching business shift for this resource shift"));

        if (dto.getStartTime().isBefore(businessShift.getStartTime()) ||
                dto.getEndTime().isAfter(businessShift.getEndTime())) {
            throw new InvalidShiftTimeException("Resource shift must fit within business shift hours");
        }

        // Save merged update
        try {
            int rows = resourceShiftRepository.update(shiftId, existing);
            if (rows == 0) {
                throw new NotFoundException("Shift with id " + shiftId + " not found");
            }

            ResourceShift updated = resourceShiftRepository.findResourceShiftById(resourceId, shiftId)
                    .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found after update"));

            return ResourceShiftMapper.toResourceShiftDTO(updated);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateShiftException("Another shift with the same slot number already exists for this resource");
        }
    }

    @Override
    @Transactional
    public void deleteResourceShift(Long businessId , Long serviceId ,Long resourceId, Long shiftId) {
        Long userId = getCurrentUserId();

        // 1. Check existence
        ResourceShift existing = resourceShiftRepository.findResourceShiftById(resourceId, shiftId)
                .orElseThrow(() -> new NotFoundException("Shift with id " + shiftId + " not found"));

        // 2. Check ownership
        if (!ownershipRepository.userOwnsResource(businessId, serviceId, resourceId, userId)) {
            throw new AccessDeniedException("You do not own this resource");
        }

        // 3. Delete
        int rows = resourceShiftRepository.delete(resourceId, shiftId);
        if (rows == 0) {
            throw new NotFoundException("Shift with id " + shiftId + " not found");
        }

    }



}
