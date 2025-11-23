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
        UserInfoDto googleUser = fetchGoogleUser(googleAccessToken);
        User user = findOrCreateUser(googleUser);
        String jwt = tokenProvider.createToken(user);
        return buildResponse(user, jwt);
    }

    private UserInfoDto fetchGoogleUser(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                UserInfoDto.class
        );

        return response.getBody();
    }

    private User findOrCreateUser(UserInfoDto googleUser) {
        return userRepository.findByEmail(googleUser.getEmail())
                .orElseGet(() -> createNewUser(googleUser));
    }

    private User createNewUser(UserInfoDto googleUser) {
        User user = User.builder()
                .email(googleUser.getEmail())
                .password(null)
                .username(googleUser.getName())
                .role(Role.ROLE_USER)
                .provider("google")
                .providerId(googleUser.getId())
                .pictureUrl(googleUser.getPicture())
                .build();

        return userRepository.save(user);
    }

    private UserLoginResponseDto buildResponse(User user, String jwt) {
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
