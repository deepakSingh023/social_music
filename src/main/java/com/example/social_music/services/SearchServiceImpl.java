package com.example.social_music.services;

import com.example.social_music.dto.SongDto;
import com.example.social_music.dto.SongSearchResponse;
import com.example.social_music.dto.spotify.SpotifyApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    @Value("${spotify.api.base-url}")
    private String baseUrl;

    private final WebClient.Builder webClientBuilder;
    private final SpotifyTokenService spotifyTokenService;

    @Override
    public SongSearchResponse searchSongs(String query, int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        // Refresh token if expired
        if (spotifyTokenService.isTokenExpired()) {
            spotifyTokenService.refreshToken();
        }

        String token = spotifyTokenService.getToken();

        Mono<SpotifyApiResponse> responseMono = webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(baseUrl)
                        .path("/v1/search")
                        .queryParam("q", query)
                        .queryParam("type", "track")
                        .queryParam("limit", pageSize)
                        .queryParam("offset", offset)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(SpotifyApiResponse.class);

        SpotifyApiResponse apiResponse = responseMono.block();

        List<SongDto> songs = apiResponse.getTracks().getItems().stream()
                .map(track -> new SongDto(
                        track.getId(),
                        track.getName(),
                        track.getArtists().get(0).getName(),
                        track.getAlbum().getImages().get(0).getUrl(),
                        track.getPreview_url()
                ))
                .collect(Collectors.toList());

        return new SongSearchResponse(songs, page, pageSize, apiResponse.getTracks().getTotal());
    }
}
