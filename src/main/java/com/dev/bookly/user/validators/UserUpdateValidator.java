package com.dev.bookly.user.validators;

import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.exceptions.UserInvalidDataException;

/**
 * {@code UserUpdateValidator} is a utility class that performs
 * input validation for {@link UserUpdateRequestDTO} objects
 * before they are passed to the service layer.
 * <p>
 * Unlike {@link UserCreationValidator}, fields in update requests
 * are optional (nullable). This validator enforces the following rules:
 * <ul>
 *   <li>If {@code firstName} is provided, it must not be blank.</li>
 *   <li>If {@code lastName} is provided, it must not be blank.</li>
 *   <li>If {@code email} is provided, it must follow a valid email format.</li>
 * </ul>
 * <p>
 * Any violation results in a {@link UserInvalidDataException}
 * being thrown to prevent further processing of invalid input.
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>The validator is stateless and exposes only static methods.</li>
 *   <li>Only fields present in the update DTO are validated.</li>
 *   <li>This keeps controller code clean and enforces consistent validation rules.</li>
 * </ul>
 *
 * Example usage:
 * <pre>{@code
 * UserUpdateRequestDTO dto = new UserUpdateRequestDTO(...);
 * UserUpdateValidator.validate(dto); // throws if invalid
 * }</pre>
 *
 * @author Helal
 * @see UserUpdateRequestDTO
 * @see UserInvalidDataException
 */
public class UserUpdateValidator {

    /**
     * Validates the given {@link UserUpdateRequestDTO}.
     * <p>
     * Only non-null fields are validated. This supports partial updates
     * (PATCH semantics) where fields not being updated can remain {@code null}.
     *
     * @param dto the request object containing user update data
     * @throws UserInvalidDataException if any provided field fails validation rules
     */
    public static void validate(UserUpdateRequestDTO dto) {
        if (dto.getFirstName() != null && dto.getFirstName().trim().isEmpty()) {
            throw new UserInvalidDataException("first name is empty!");
        }
        if (dto.getLastName() != null && dto.getLastName().trim().isEmpty()) {
            throw new UserInvalidDataException("last name is empty!");
        }
        if (dto.getEmail() != null
                && !dto.getEmail().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new UserInvalidDataException("invalid email format!");
        }
    }
}
