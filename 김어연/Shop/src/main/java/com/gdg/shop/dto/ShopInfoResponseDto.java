package com.gdg.shop.dto;

import com.gdg.shop.domain.Shop;
import lombok.Getter;

@Getter
public class ShopInfoResponseDto {
    private Long id;
    private String productName;
    private Integer price;
    private Integer quantity;
    private Long userId;

    public ShopInfoResponseDto(Shop shop) {
        this.id = shop.getId();
        this.productName = shop.getProductName();
        this.price = shop.getPrice();
        this.quantity = shop.getQuantity();
        this.userId = shop.getUser().getId();
    }
}
