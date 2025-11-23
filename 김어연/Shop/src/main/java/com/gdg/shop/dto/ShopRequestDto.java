package com.gdg.shop.dto;

public record ShopRequestDto(
        Long userId,
        String productName,
        Integer price,
        Integer quantity
) {}
