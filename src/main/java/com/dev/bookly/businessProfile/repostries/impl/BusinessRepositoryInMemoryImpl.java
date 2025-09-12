package com.dev.bookly.businessProfile.repostries.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.repostries.BusinessRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BusinessRepositoryInMemoryImpl implements BusinessRepository {

    private Map<Long, Business> businesses = new HashMap<>();

    public BusinessRepositoryInMemoryImpl() {}

//    @Override
//    public Business findById(Long userId, Long businessId) {
//        return null;
//    }

    @Override
    public Business save(Business business) {
        return null;
    }

    @Override
    public List<Business> findAll(Long userId) {
        return List.of();
    }

    @Override
    public Business update(Business business) {
        return null;
    }

    @Override
    public Business status(Long id, boolean active, Long userId) {
        return null;
    }
}
