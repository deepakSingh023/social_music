package com.example.social_music.dto.spotify;

import lombok.Data;

import java.util.List;

@Data
public class Track {
    private String id;
    private String name;
    private List<Artist> artists;
    private Album album;
    private String preview_url;   // NOTE: Spotify returns preview_url (snake_case)
}
