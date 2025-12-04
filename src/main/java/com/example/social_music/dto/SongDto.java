package com.example.social_music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {
    private String id;
    private String name;
    private String artist;
    private String albumArtUrl;
    private String previewUrl; // 30s preview from Spotify
}
