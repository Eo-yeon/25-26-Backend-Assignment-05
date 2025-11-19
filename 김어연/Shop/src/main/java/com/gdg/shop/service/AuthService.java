package com.gdg.shop.service;

import com.gdg.shop.domain.Role;
import com.gdg.shop.domain.User;
import com.gdg.shop.dto.GoogleTokenResponse;
import com.gdg.shop.dto.UserInfoDto;
import com.gdg.shop.dto.UserLoginResponseDto;
import com.gdg.shop.jwt.TokenProvider;
import com.gdg.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGoogleAccessToken(String code) {

        String url = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body =
                "code=" + code +
                        "&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&redirect_uri=" + redirectUri +
                        "&grant_type=authorization_code";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<GoogleTokenResponse> response =
                restTemplate.postForEntity(url, request, GoogleTokenResponse.class);

        return response.getBody().getAccessToken();
    }

    public UserLoginResponseDto loginOrSignUp(String googleAccessToken) {

        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(googleAccessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDto> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                UserInfoDto.class
        );

        UserInfoDto googleUser = response.getBody();

        String email = googleUser.getEmail();
        String username = googleUser.getName();
        String pictureUrl = googleUser.getPicture();
        String providerId = googleUser.getId();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = User.builder()
                    .email(email)
                    .password(null)
                    .username(username)
                    .role(Role.ROLE_USER)
                    .provider("google")
                    .providerId(providerId)
                    .pictureUrl(pictureUrl)
                    .build();

            userRepository.save(user);
        }

        String jwt = tokenProvider.createToken(user);

        return UserLoginResponseDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .provider(user.getProvider())
                .pictureUrl(user.getPictureUrl())
                .accessToken(jwt)
                .build();
    }
}