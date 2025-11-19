package com.gdg.shop.controller;

import com.gdg.shop.dto.UserLoginResponseDto;
import com.gdg.shop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/oauth2")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/authorize/google")
    public ResponseEntity<Void> googleAuthorize() {

        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=108606505948-r3tm42moa646p4lmpeecakfv1sm3i0us.apps.googleusercontent.com"
                + "&redirect_uri=http://localhost:8080/api/oauth2/callback/google"
                + "&response_type=code"
                + "&scope=openid%20email%20profile";

        return ResponseEntity.status(302)
                .header("Location", url)
                .build();
    }

    @GetMapping("/callback/google")
    public UserLoginResponseDto googleCallback(@RequestParam("code") String code) {
        String googleAccessToken = authService.getGoogleAccessToken(code);
        return authService.loginOrSignUp(googleAccessToken);
    }

}