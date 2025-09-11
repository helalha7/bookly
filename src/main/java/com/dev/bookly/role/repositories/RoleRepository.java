package com.dev.bookly.role.repositories;

import com.dev.bookly.role.domains.Role;

public interface RoleRepository {
    Role getRoleByName(String name);
}
