package com.fundoonotes.service;

import com.fundoonotes.dto.request.UserRegisterRequestDto;
import com.fundoonotes.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegisterRequestDto requestDto);
}
