package com.example.social_music.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeezerAlbum {
    @JsonProperty("cover_medium")
    private String coverMedium;
}