package com.example.social_music.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("previewUrl")  // This will serialize to camelCase for frontend
    private String preview_url;   // But use snake_case internally to match Spotify
}