package com.dev.bookly.user.controllers;

import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;
import com.dev.bookly.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserAdminController {
    private UserService userService;

    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return new ResponseEntity<>(userResponseDTO,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreationRequestDTO userCreationRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userCreationRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserInfo(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<Void> updateUserAccountStatus(@PathVariable Long userId, @RequestBody UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/password-reset")
    public ResponseEntity<Void> triggerPasswordReset(@PathVariable Long userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
