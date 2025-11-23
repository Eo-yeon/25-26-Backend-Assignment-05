package com.gdg.shop.dto;

public record UserLoginResponseDto(
        Long id,
        String name,
        String email,
        String provider,
        String pictureUrl,
        String accessToken
){}
