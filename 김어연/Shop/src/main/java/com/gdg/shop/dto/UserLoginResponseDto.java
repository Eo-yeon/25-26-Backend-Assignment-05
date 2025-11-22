package com.gdg.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private Long id;
    private String name;
    private String email;
    private String provider;
    private String pictureUrl;
    private String accessToken;
}
