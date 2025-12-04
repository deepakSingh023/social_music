package com.example.social_music.services;

import com.example.social_music.dto.spotify.SpotifyTokenResponse;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Base64;

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

    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        refreshToken();
    }

    public synchronized void refreshToken() {

        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        var headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        var entity = new org.springframework.http.HttpEntity<>("grant_type=client_credentials", headers);

        var response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token",
                entity,
                SpotifyTokenResponse.class
        );

        var body = response.getBody();

        if (body != null) {
            this.token = body.getAccess_token();
            this.expiryTime = Instant.now().plusSeconds(body.getExpires_in());
            System.out.println("Spotify token refreshed at " + Instant.now());
        }
    }

    public boolean isTokenExpired() {
        return token == null || Instant.now().isAfter(expiryTime);
    }
}
