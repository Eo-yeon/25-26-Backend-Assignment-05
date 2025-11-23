package com.gdg.shop.dto;

public record UserInfoDto(
        String id,
        String email,
        Boolean verified_email,
        String name,
        String picture
) {}
