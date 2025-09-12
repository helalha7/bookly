package com.dev.bookly.businessProfile.dtos.request;

import java.util.Date;

public class BusinessRequestDTO {
    private final String name;
    private final String address;
    private final String logoUrl;
    private final String description;
    private final String timeZone;




    public BusinessRequestDTO(String name, String address, String logoUrl, String description, String timeZone) {
        this.name = name;
        this.address = address;
        this.logoUrl = logoUrl;
        this.description = description;
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeZone() {
        return timeZone;
    }


}
