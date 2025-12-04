package com.example.social_music.dto.spotify;

import lombok.Data;

import java.util.List;

@Data
public class Tracks {
    private List<Track> items;
    private int total;
}
