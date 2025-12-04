package com.example.social_music.dto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SongSearchResponse {

    private List<SongDto> songs;
    private int page;
    private int pageSize;
    private int total;
}
