package com.dev.bookly.role.services;

import com.dev.bookly.role.dtos.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    void createRole(RoleDTO roleDTO);
    void deleteRole(Long roleId);
    void updateRole(Long role , RoleDTO roleDTO);
}
