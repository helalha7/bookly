package com.dev.bookly.auth.services;

import com.dev.bookly.auth.dtos.request.RegisterRequestDTO;
import com.dev.bookly.auth.dtos.response.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);
}
