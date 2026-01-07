package com.example.social_music.services;

import com.example.social_music.dto.SongDto;
import com.example.social_music.dto.SongSearchResponse;
import com.example.social_music.dto.spotify.SpotifyApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    @Value("${spotify.api.base-url}")
    private String baseUrl;

    private final SpotifyTokenService spotifyTokenService;

    private final RestTemplate restTemplate = new RestTemplate();

    // In SearchServiceImpl.java - UPDATE THIS:

    @Override
    public SongSearchResponse searchSongs(String query, int page, int pageSize) {

        int offset = (page - 1) * pageSize;

        if (spotifyTokenService.isTokenExpired()) {
            spotifyTokenService.refreshToken();
        }

        String token = spotifyTokenService.getToken();

        // ADD &market=US to increase preview availability
        String url = "https://" + baseUrl + "/v1/search?q=" + query +
                "&type=track&limit=" + pageSize + "&offset=" + offset + "&market=US";

        var headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        var entity = new org.springframework.http.HttpEntity<>(headers);

        var response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                SpotifyApiResponse.class
        );

        SpotifyApiResponse apiResponse = response.getBody();

        List<SongDto> songs = apiResponse.getTracks().getItems().stream()
                .map(track -> new SongDto(
                        track.getId(),
                        track.getName(),
                        track.getArtists().get(0).getName(),
                        track.getAlbum().getImages().get(0).getUrl(),
                        track.getPreview_url()
                ))
                .collect(Collectors.toList());

        return new SongSearchResponse(
                songs,
                page,
                pageSize,
                apiResponse.getTracks().getTotal()
        );
    }
}
