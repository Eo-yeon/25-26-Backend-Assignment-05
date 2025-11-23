package com.gdg.shop.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class GoogleTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("id_token")
    private String idToken;
}
