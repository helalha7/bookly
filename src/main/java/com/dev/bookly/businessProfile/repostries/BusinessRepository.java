package com.dev.bookly.businessProfile.repostries;

import com.dev.bookly.businessProfile.domains.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository {
//    public Business findById(Long userId,Long businessId);
    public Business save(Business business);
    public List<Business>  findAll(Long userId);
    public Business update(Business business);
    public Business status(Long id,boolean active,Long userId);
}
