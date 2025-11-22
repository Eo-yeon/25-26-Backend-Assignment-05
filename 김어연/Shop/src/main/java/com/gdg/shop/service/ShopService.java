package com.gdg.shop.service;

import com.gdg.shop.domain.Shop;
import com.gdg.shop.domain.User;
import com.gdg.shop.dto.ShopInfoResponseDto;
import com.gdg.shop.dto.ShopRequestDto;
import com.gdg.shop.repository.ShopRepository;
import com.gdg.shop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Transactional
    public ShopInfoResponseDto addShop(Long loginUserId, ShopRequestDto dto) {

        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Shop shop = Shop.builder()
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .user(user)
                .build();

        return new ShopInfoResponseDto(shopRepository.save(shop));
    }

    @Transactional(readOnly = true)
    public List<ShopInfoResponseDto> getShopByUserId(Long loginUserId) {
        return shopRepository.findByUserId(loginUserId)
                .stream()
                .map(ShopInfoResponseDto::new)
                .toList();
    }

    @Transactional
    public ShopInfoResponseDto updateShop(Long loginUserId, Long shopId, ShopRequestDto dto) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found"));

        if (!shop.getUser().getId().equals(loginUserId)) {
            throw new SecurityException("본인 Shop만 수정할 수 있습니다.");
        }

        shop.update(dto.getProductName(), dto.getPrice(), dto.getQuantity());

        return new ShopInfoResponseDto(shop);
    }

    @Transactional
    public void deleteShop(Long loginUserId, Long shopId) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found"));

        if (!shop.getUser().getId().equals(loginUserId)) {
            throw new SecurityException("본인 Shop만 삭제할 수 있습니다.");
        }

        shopRepository.delete(shop);
    }
}
