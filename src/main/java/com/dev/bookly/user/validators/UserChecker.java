package com.dev.bookly.user.validators;

import com.dev.bookly.user.exceptions.UserInvalidDataException;

public class UserChecker {

    public static void checkFirstName(String firstName) {
        if(firstName == null) {
            throw new UserInvalidDataException("first name is null");
        }
        if(firstName.trim().isEmpty()) {
            throw new UserInvalidDataException("first name is empty");
        }
    }

    public static void checkLastName(String lastName) {
        if(lastName == null) {
            throw new UserInvalidDataException("first name is null");
        }
        if(lastName.trim().isEmpty()) {
            throw new UserInvalidDataException("first name is empty");
        }
    }

    public static void checkUsername(String username) {
        if(username == null) {
            throw new UserInvalidDataException("username is null");
        }
        if(username.trim().length() < 5 || username.trim().length() > 30) {
            throw new UserInvalidDataException("username length should be between 5 and 30");
        }
    }

    public static void checkEmail(String email) {
        if (email == null) {
            throw new UserInvalidDataException("Email is null");
        }
        email = email.trim();
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new UserInvalidDataException("Invalid email format");
        }
    }

    public static void checkPassword(String password) {
        if (password == null) {
            throw new UserInvalidDataException("Password is null");
        }
        password = password.trim();
        if(password.length() < 8) {
            throw new UserInvalidDataException("Password length must be at least 8 characters");
        }
    }
}
