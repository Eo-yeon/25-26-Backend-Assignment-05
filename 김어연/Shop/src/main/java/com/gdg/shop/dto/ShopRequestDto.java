package com.gdg.shop.dto;

import lombok.Getter;

@Getter
public class ShopRequestDto {
    private Long userId;
    private String productName;
    private Integer price;
    private Integer quantity;
}
