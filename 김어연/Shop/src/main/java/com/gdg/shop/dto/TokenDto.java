package com.gdg.shop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    private String accessToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
