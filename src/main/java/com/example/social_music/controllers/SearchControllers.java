package com.example.social_music.controllers;

import com.example.social_music.dto.SongSearchResponse;
import com.example.social_music.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
public class SearchControllers {

    private final SearchService spotifySearchService;

    @GetMapping("/search")
    public SongSearchResponse searchSongs(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return spotifySearchService.searchSongs(query, page, pageSize);
    }
}
