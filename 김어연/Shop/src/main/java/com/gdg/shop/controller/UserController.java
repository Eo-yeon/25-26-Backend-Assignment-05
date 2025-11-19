package com.gdg.shop.controller;

import com.gdg.shop.dto.UserInfoResponseDto;
import com.gdg.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoResponseDto> getUserInfo(Principal principal) {
        return ResponseEntity.ok(userService.findUserByPrincipal(principal));
    }
}
