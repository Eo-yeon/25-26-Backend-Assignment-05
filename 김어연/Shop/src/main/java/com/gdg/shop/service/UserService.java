package com.gdg.shop.service;

import com.gdg.shop.domain.User;
import com.gdg.shop.dto.UserInfoResponseDto;
import com.gdg.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponseDto findUserByPrincipal(Principal principal) {
        Long userId = Long.valueOf(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        return UserInfoResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
