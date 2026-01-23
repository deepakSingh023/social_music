package com.example.social_music.dto;

import lombok.Data;

@Data
public class DeezerTrack {
    private long id;
    private String title;
    private String preview;
    private DeezerArtist artist;
    private DeezerAlbum album;
}