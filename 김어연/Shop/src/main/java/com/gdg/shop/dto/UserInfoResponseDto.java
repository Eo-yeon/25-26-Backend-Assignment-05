package com.gdg.shop.dto;

import com.gdg.shop.domain.Role;

public record UserInfoResponseDto(
        Long id,
        String email,
        String password,
        String username,
        Role role
){}
