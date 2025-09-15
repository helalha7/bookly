package com.dev.bookly.service.servises.Impl;

import com.dev.bookly.service.repositories.OwnershipRepository;
import com.dev.bookly.service.servises.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OwnershipServiceImpl implements OwnershipService {

    private final OwnershipRepository ownershipRepository;

    @Autowired
    public OwnershipServiceImpl(OwnershipRepository ownershipRepository) {
        this.ownershipRepository = ownershipRepository;
    }

    @Override
    public void userOwnsBusiness(Long businessId, String username) {

        if (!ownershipRepository.userOwnsBusiness(businessId, username) && !isAdmin()) {
            throw new AccessDeniedException("You do not own this business");
        }
    }


    @Override
    public void userOwnsService(Long businessId, Long servicesId, String username) {
        if (!ownershipRepository.userOwnsService(businessId, servicesId, username) && !isAdmin()) {
            throw new AccessDeniedException("You do not own this service");
        }
    }

    @Override
    public void userOwnsResource(Long businessId, Long servicesId, Long resourceId, String username) {
        if (!ownershipRepository.userOwnsResource(businessId, servicesId, resourceId, username) && !isAdmin()) {
            throw new AccessDeniedException("You do not own this resource");
        }
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }
}
