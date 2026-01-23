package com.example.social_music.controllers;

import com.example.social_music.dto.SongSearchResponse;
import com.example.social_music.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music") // now generic
@RequiredArgsConstructor

public class SearchControllers {

    private final SearchService deezerSearchService;

    @GetMapping("/search")
    public ResponseEntity<SongSearchResponse> searchSongs(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            SongSearchResponse response = deezerSearchService.searchSongs(query, page, pageSize);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
