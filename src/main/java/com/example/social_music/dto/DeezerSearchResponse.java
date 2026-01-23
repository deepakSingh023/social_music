package com.example.social_music.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeezerSearchResponse {
    private List<DeezerTrack> data;
    private int total;
}