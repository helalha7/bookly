package com.dev.bookly.user.controllers;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PageResponseDTO;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;
import com.dev.bookly.user.services.UserService;
import com.dev.bookly.user.validators.UserCreationValidator;
import com.dev.bookly.user.validators.UserIdValidator;
import com.dev.bookly.user.validators.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserAdminController {


    private final UserService userService;

    @Autowired
    public UserAdminController(UserService userService)  {
        this.userService = userService;
    }




    @GetMapping
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageRequestDTO pr = new PageRequestDTO(page, size);
        PageResponseDTO<UserResponseDTO> response= userService.getAllUsers(pr);
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }



    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserIdValidator.validate(userId);
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return new ResponseEntity<>(userResponseDTO,HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreationRequestDTO userCreationRequestDTO) {
        UserCreationValidator.validate(userCreationRequestDTO);
        UserResponseDTO userResponseDTO = userService.createUser(userCreationRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        UserIdValidator.validate(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserInfo(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        UserIdValidator.validate(userId);
        UserUpdateValidator.validate(userUpdateRequestDTO);
        userService.updateUserInfo(userId, userUpdateRequestDTO);
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
