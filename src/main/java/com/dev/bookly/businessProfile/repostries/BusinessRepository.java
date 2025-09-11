package com.dev.bookly.businessProfile.repostries;

import com.dev.bookly.businessProfile.domains.Business;

import java.util.Optional;

public interface BusinessRepository {
    public Optional<Business>  findById(Long id);
    public Business save(Business business);

}
