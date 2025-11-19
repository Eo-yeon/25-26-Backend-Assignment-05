package com.gdg.shop.dto;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String picture;
}