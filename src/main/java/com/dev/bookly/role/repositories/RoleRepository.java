package com.dev.bookly.role.repositories;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;

import java.util.List;

public interface RoleRepository {
    Role getRoleByName(String name);

    List<Role> getAllRoles();
    void createRole(Role role);
    void deleteRole(Long roleId);
    void updateRole(Long roleId, Role role);

    boolean existsByName(String role);
    boolean existsById(Long roleId);
    boolean canDelete(Long roleId);
}
