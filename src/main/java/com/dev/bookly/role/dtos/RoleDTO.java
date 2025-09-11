package com.dev.bookly.role.dtos;

public class RoleDTO {
    private final Integer id;
    private final String role;

    public RoleDTO(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
