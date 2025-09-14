package com.dev.bookly.businessProfile.repostries;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTOAdmin;

import java.util.List;

public interface BusinessRepository {
    public Business findById(Long userId);
    public Business save(Business business);
    public List<Business>  findAll(Long userId);
    public Business update(Business business);
    public Business status(Long id,boolean active,Long userId);
    public List<Business> getAllUsersBusinesses();
    public Business getBusinessByUserId(Long userId);
    Long delete(Long id);
}
