package com.dev.bookly.role.controllers;


import com.dev.bookly.role.dtos.RoleDTO;
import com.dev.bookly.role.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles(){
        List<RoleDTO> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody RoleDTO roleDTO){
        roleService.createRole(roleDTO);
        return new ResponseEntity<>("The Role " +roleDTO.getRole() +" Was Created Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Long roleId){
        roleService.deleteRole(roleId);
        return new ResponseEntity<>("The Role " +roleId + " Was Deleted Successfully", HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<String> updateRole(@PathVariable Long roleId, @RequestBody RoleDTO roleDTO){
        roleService.updateRole(roleId,roleDTO);
        return new ResponseEntity<>("The Role " +roleId + " Was Updated Successfully", HttpStatus.OK);
    }
}
