package com.gdg.shop.controller;

import com.gdg.shop.dto.ShopInfoResponseDto;
import com.gdg.shop.dto.ShopRequestDto;
import com.gdg.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public ResponseEntity<ShopInfoResponseDto> addShop(
            @RequestBody ShopRequestDto shopRequestDto
    ) {
        ShopInfoResponseDto responseDto = shopService.addShop(
                shopRequestDto.getUserId(),
                shopRequestDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ShopInfoResponseDto>> getShopsByUser(
            Principal principal
    ) {
        long userId = Long.parseLong(principal.getName());
        List<ShopInfoResponseDto> shops = shopService.getShopByUserId(userId);
        return ResponseEntity.ok(shops);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopInfoResponseDto> updateShop(
            @PathVariable Long shopId,
            @RequestBody ShopRequestDto shopRequestDto,
            Principal principal
    ) {
        Long userId = Long.valueOf(principal.getName());

        ShopInfoResponseDto responseDto =
                shopService.updateShop(userId, shopId, shopRequestDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> deleteShop(
            @PathVariable Long shopId,
            Principal principal
    ) {
        Long userId = Long.valueOf(principal.getName());
        shopService.deleteShop(userId, shopId);
        return ResponseEntity.noContent().build();
    }
}
