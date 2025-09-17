package com.dev.bookly.user.validators;

import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.exceptions.UserInvalidDataException;

/**
 * {@code UserCreationValidator} is a utility class that performs
 * input validation for {@link UserCreationRequestDTO} objects
 * before they are passed to the service layer.
 * <p>
 * This validator enforces basic syntactic rules such as:
 * <ul>
 *   <li>First name and last name must not be {@code null} or blank.</li>
 *   <li>Username length must be between 5 and 30 characters.</li>
 *   <li>Email must follow a valid email address format.</li>
 *   <li>Password must be at least 8 characters long.</li>
 * </ul>
 * <p>
 * If any validation rule fails, a {@link UserInvalidDataException}
 * is thrown to prevent further processing of invalid input.
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>The class is declared {@code final} to prevent extension,
 *       as it only provides static utility behavior.</li>
 *   <li>The {@code validate} method is static to allow direct use
 *       without object instantiation.</li>
 * </ul>
 *
 * Example usage:
 * <pre>{@code
 * UserCreationRequestDTO dto = new UserCreationRequestDTO(...);
 * UserCreationValidator.validate(dto); // throws if invalid
 * }</pre>
 *
 * @author Helal
 * @see UserCreationRequestDTO
 * @see UserInvalidDataException
 */
public final class UserCreationValidator {

    /**
     * Validates the given {@link UserCreationRequestDTO}.
     *
     * @param dto the request object containing user creation data
     * @throws UserInvalidDataException if any field fails validation rules
     */
    public static void validate(UserCreationRequestDTO dto) {
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new UserInvalidDataException("first name is empty!");
        }
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new UserInvalidDataException("last name is empty!");
        }
        if (dto.getUsername() == null
                || dto.getUsername().length() < 5
                || dto.getUsername().length() > 30) {
            throw new UserInvalidDataException("username length must be between 5 and 30");
        }
        if (dto.getEmail() == null
                || !dto.getEmail().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new UserInvalidDataException("Invalid email format");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().length() < 8) {
            throw new UserInvalidDataException("password length must be 8 at least");
        }
    }
}
