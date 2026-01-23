package com.example.social_music.services;

import com.example.social_music.dto.DeezerSearchResponse;
import com.example.social_music.dto.SongDto;
import com.example.social_music.dto.SongSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SearchServiceImpl implements SearchService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public SongSearchResponse searchSongs(String query, int page, int pageSize) {
        int index = (page - 1) * pageSize;

        String url = "https://api.deezer.com/search?q=" + query
                + "&limit=" + pageSize + "&index=" + index;

        var response = restTemplate.getForObject(url, DeezerSearchResponse.class);

        List<SongDto> songs = response.getData().stream()
                .map(track -> new SongDto(
                        String.valueOf(track.getId()),
                        track.getTitle(),
                        track.getArtist().getName(),
                        track.getAlbum().getCoverMedium(),
                        track.getPreview() // 30-sec MP3 preview
                ))
                .collect(Collectors.toList());

        return new SongSearchResponse(
                songs,
                page,
                pageSize,
                response.getTotal()
        );
    }
}
