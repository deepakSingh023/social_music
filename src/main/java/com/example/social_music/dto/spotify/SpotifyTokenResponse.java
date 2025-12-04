package com.example.social_music.dto.spotify;

import lombok.Data;

@Data
public class SpotifyTokenResponse {
    private String access_token;
    private String token_type;
    private long expires_in;
}
