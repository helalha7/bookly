package com.dev.bookly.role.dtos;

import com.dev.bookly.role.domains.Role;

public class RoleMapper {

    public static Role toRole(RoleDTO roleDTO) {
        Role role = new Role(
                roleDTO.getId(),
                roleDTO.getRole()
        );
        return role;
    }

    public static RoleDTO toRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO(
                role.getId(),
                role.getRole()
        );
        return roleDTO;
    }
}
