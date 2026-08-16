package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.UserRegisterRequestDto;
import com.fundoonotes.dto.response.UserResponseDto;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.UserAlreadyExistsException;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegisterRequestDto requestDto) {
        log.info("Registering user with email: {}", requestDto.getEmail());
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + requestDto.getEmail());
        }
        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User saved = userRepository.save(user);
        log.info("User registered successfully with id: {}", saved.getId());
        return new UserResponseDto(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), "User registered successfully");
    }
}
