package com.dev.bookly.user.controllers;

import com.dev.bookly.user.dtos.requests.UserChangePasswordRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;
import com.dev.bookly.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class UserMeController {
    private UserService userService;

    @Autowired
    public UserMeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getMyInfo(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponseDTO userResponseDTO = userService.getUserByUsername(
                userDetails.getUsername()
        );
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateMyInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changeMyPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserChangePasswordRequestDTO userChangePasswordRequestDTO
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
