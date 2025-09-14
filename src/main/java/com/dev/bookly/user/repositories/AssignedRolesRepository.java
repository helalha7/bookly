package com.dev.bookly.user.repositories;

import com.dev.bookly.role.domains.Role;

import java.util.List;

public interface AssignedRolesRepository {
    void assignRoles(Long accountId, List<Role> roles);

}
