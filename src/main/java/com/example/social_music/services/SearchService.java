package com.example.social_music.services;

import com.example.social_music.dto.SongSearchResponse;

public interface SearchService {
    SongSearchResponse searchSongs(String query, int page, int pageSize);
}
