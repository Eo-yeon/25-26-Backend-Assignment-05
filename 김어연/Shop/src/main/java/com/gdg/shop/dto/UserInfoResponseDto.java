package com.gdg.shop.dto;

import com.gdg.shop.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String email;
    private String password;
    private String username;
    private Role role;
}
