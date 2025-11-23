package com.gdg.shop.dto;

import com.google.gson.annotations.SerializedName;

public record GoogleTokenResponse(
        @SerializedName("access_token") String accessToken,
        @SerializedName("expires_in") int expiresIn,
        @SerializedName("scope") String scope,
        @SerializedName("token_type") String tokenType,
        @SerializedName("id_token") String idToken
) {}
