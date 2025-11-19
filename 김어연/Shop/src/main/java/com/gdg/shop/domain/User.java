package com.gdg.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    private String provider;

    @Column(nullable = true)
    private String providerId;

    @Column(nullable = true)
    private String pictureUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shop> orders = new ArrayList<>();

    @Builder
    public User(String email,
                String password,
                String username,
                Role role,
                String provider,
                String providerId,
                String pictureUrl) {

        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.pictureUrl = pictureUrl;
    }
}