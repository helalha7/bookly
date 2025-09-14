package com.dev.bookly.businessProfile.dtos.response;

import java.util.Date;

public class BusinessResponseDTOAdmin {

        private final Long id;
        private final Long userId;
        private final String name;
        private final String address;
        private final String logoUrl;
        private final String description;
        private final String timeZone;
        private final boolean active;
        private final Date createdAt;


        public BusinessResponseDTOAdmin(Long id, Long userId, String name, String address, String logoUrl, String description, String timeZone, boolean active, Date createdAt) {
            this.id = id;
            this.userId = userId;
            this.name = name;
            this.address = address;
            this.logoUrl = logoUrl;
            this.description = description;
            this.timeZone = timeZone;
            this.active = active;

            this.createdAt = createdAt;
        }

        public Long getId() {
            return id;
        }

    public Long getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
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

        public boolean isActive() {
            return active;
        }
    }


