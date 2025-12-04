package com.example.social_music.services;

import com.example.social_music.dto.spotify.SpotifyTokenResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
@Getter
@Setter
public class SpotifyTokenService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String token;
    private Instant expiryTime;

    private final WebClient webClient;

    public SpotifyTokenService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        refreshToken(); // refresh token at startup
    }

    public synchronized void refreshToken() {
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());

        var response = webClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(SpotifyTokenResponse.class)
                .block();

        if (response != null) {
            this.token = response.getAccess_token();
            this.expiryTime = Instant.now().plusSeconds(response.getExpires_in());
            System.out.println("Spotify token refreshed at " + Instant.now());
        }
    }

    public boolean isTokenExpired() {
        return token == null || Instant.now().isAfter(expiryTime);
    }
}
