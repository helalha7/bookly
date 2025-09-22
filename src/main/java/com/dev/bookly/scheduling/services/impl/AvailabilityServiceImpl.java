package com.dev.bookly.scheduling.services.impl;

import com.dev.bookly.booking.domains.Booking;
import com.dev.bookly.booking.repositories.BookingRepository;
import com.dev.bookly.scheduling.domains.ResourceShift;
import com.dev.bookly.scheduling.dtos.LocalSlotDTO;
import com.dev.bookly.scheduling.exceptions.*;
import com.dev.bookly.scheduling.repository.ResourceShiftRepository;
import com.dev.bookly.scheduling.services.AvailabilityService;
import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.domain.Service;
import com.dev.bookly.service.exceptions.resourcesExceptions.InvalidCapacityException;
import com.dev.bookly.service.exceptions.resourcesExceptions.ResourceNotFoundException;
import com.dev.bookly.service.exceptions.servicesExceptions.ServiceNotFoundException;
import com.dev.bookly.service.repositories.ResourceRepository;
import com.dev.bookly.service.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;


import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private ServiceRepository serviceRepo;
    private ResourceRepository resourceRepo;
    private ResourceShiftRepository shiftRepo;
    private BookingRepository bookingRepo;

    @Autowired
    public AvailabilityServiceImpl(ServiceRepository serviceRepo, ResourceRepository resourceRepo, ResourceShiftRepository shiftRepo, BookingRepository bookingRepo) {
        this.serviceRepo = serviceRepo;
        this.resourceRepo = resourceRepo;
        this.shiftRepo = shiftRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<LocalSlotDTO> listSlots(Long serviceId, Long resourceId, String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        }catch (DateTimeParseException ex){
            throw new InvalidDateFormatException("Date must be in format yyyy-MM-dd, got: " + date);
        }
//        if (localDate.isBefore(LocalDate.now())) {
//            throw new PastDateNotAllowedException("Date cannot be in the past: " + localDate);
//        }
        try {
            // 1. Load service
            Service service = serviceRepo.findById(serviceId);

            if (service == null) {
                throw new ServiceNotFoundException("Service not found: " + serviceId);
            }

            int duration = service.getDurationMins();

            // 2. Load resource
            Resource resource = resourceRepo.getResourceById(resourceId);

            if (resource == null) {
                throw new ResourceNotFoundException("Resource not found: " + resourceId);
            }

            int capacity = resource.getCapacity();

            // 3. Load effective resource shift
            List<ResourceShift> shifts = shiftRepo.findEffectiveResourceAndDate(resourceId, localDate);

        if (shifts.isEmpty()) {
            throw new ShiftsNotFoundException("No shifts available for resource on " + localDate);
        }


            List<LocalSlotDTO> availableSlots = new ArrayList<>();

            // 4. For each shift, divide into slots
            for (ResourceShift shift : shifts) {
                LocalTime start = shift.getStartTime();
                LocalTime end = shift.getEndTime();

                while (!start.plusMinutes(duration).isAfter(end)) {
                    LocalTime slotEnd = start.plusMinutes(duration);

                    // 5. Check availability for this slot
                    LocalDateTime startSlotDateTime = LocalDateTime.of(localDate, start);
                    LocalDateTime endSlotDateTime = LocalDateTime.of(localDate, slotEnd);

                    ZoneId zone = ZoneId.systemDefault();

                    Instant startInstant = startSlotDateTime.atZone(zone).toInstant();
                    Instant endInstant = endSlotDateTime.atZone(zone).toInstant();

                    if (isAvailable(resourceId, startInstant, endInstant)) {

                        // 6. Get the numbers of booking in this date and time
                        List<Booking> overlapping = bookingRepo.findOverlapping(resourceId, startInstant, endInstant);
                        availableSlots.add(new LocalSlotDTO(startSlotDateTime, endSlotDateTime, capacity - overlapping.size()));
                    }

                    // 7. Move to next slot
                    start = slotEnd; // move to next slot
                }
            }

            return availableSlots;
        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error while listing slots");
        } catch (ServiceNotFoundException | ResourceNotFoundException | ShiftsNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException("Unexpected error while listing slots");
        }
    }

    @Override
    public boolean isAvailable(Long resourceId, Instant start, Instant end) {
        try {
            // get resource for capacity
            Resource resource = resourceRepo.getResourceById(resourceId);

            if (resource == null) {
                throw new ResourceNotFoundException("Resource not found: " + resourceId);
            }

            int capacity = resource.getCapacity();

            if (capacity <= 0) {
                throw new InvalidCapacityException("Resource " + resourceId + " has invalid capacity: " + capacity);
            }

            //find overlapping booking
            List<Booking> overlapping = bookingRepo.findOverlapping(resourceId, start, end);

            return overlapping.size() < capacity;
        }catch (DataAccessException ex) {
            throw new DatabaseException("Database error while listing slots or getting available resources");
        }
    }

}
