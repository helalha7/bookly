package com.dev.bookly.role.services.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;
import com.dev.bookly.role.dtos.RoleMapper;
import com.dev.bookly.role.exceptions.RoleAlreadyExistsException;
import com.dev.bookly.role.exceptions.RoleInUseException;
import com.dev.bookly.role.exceptions.RoleInvalidDataException;
import com.dev.bookly.role.exceptions.RoleNotFoundException;
import com.dev.bookly.role.repositories.RoleRepository;
import com.dev.bookly.role.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.getAllRoles();
        List<RoleDTO> roleDTOs = new ArrayList<>();
        for (Role role : roles) {
            RoleDTO roleDto = RoleMapper.toRoleDTO(role);
            roleDTOs.add(roleDto);
        }
        return roleDTOs;
    }

    @Override
    public void createRole(RoleDTO roleDTO) {
        Role role =  RoleMapper.toRole(roleDTO);

        if (roleDTO.getRole() == null || roleDTO.getRole().isBlank()) {
            throw new RoleInvalidDataException("Role name cannot be empty.");
        }

        if (!roleDTO.getRole().matches("[A-Za-z ]+")) {
            throw new RoleInvalidDataException("Role name can only contain letters and spaces.");
        }

        if (roleRepository.existsByName(role.getRole())) {
            throw new RoleAlreadyExistsException("Role '" + role.getRole() + "' already exists.");
        }

        roleRepository.createRole(role);
    }

    @Override
    public void deleteRole(Long roleId) {

        if(!roleRepository.canDelete(roleId)){
            throw new RoleInUseException("Role is assigned to users and cannot be deleted.");
        }
        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException("Role '" + roleId + "' is not exists.");
        }

        roleRepository.deleteRole(roleId);
    }

    @Override
    public void updateRole(Long roleId, RoleDTO roleDTO) {
        Role role =  RoleMapper.toRole(roleDTO);

        if (roleDTO.getRole().isBlank()) {
            throw new RoleInvalidDataException("Role name cannot be empty.");
        }

        if (!roleDTO.getRole().matches("[A-Za-z ]+")) {
            throw new RoleInvalidDataException("Role name can only contain letters and spaces.");
        }

        if (roleRepository.existsByName(role.getRole())) {
            throw new RoleAlreadyExistsException("Role '" + role.getRole() + "' already exists.");
        }

        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException("Role '" + roleId + "' is not exists.");
        }

        roleRepository.updateRole(roleId , role);
    }
}
