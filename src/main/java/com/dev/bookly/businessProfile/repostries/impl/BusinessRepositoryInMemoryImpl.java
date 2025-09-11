package com.dev.bookly.businessProfile.repostries.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.repostries.BusinessRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BusinessRepositoryInMemoryImpl implements BusinessRepository {

    private Map<Long, Business> businesses = new HashMap<>();

    public BusinessRepositoryInMemoryImpl() {}

    @Override
    public Optional<Business> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Business save(Business business) {
        return null;
    }
}
