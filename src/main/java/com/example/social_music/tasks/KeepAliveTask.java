package com.example.social_music.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveTask {

    private static final String PING_URL =
            "https://social-music.onrender.com/api/health";

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void pingSelf() {
        try {
            restTemplate.getForObject(PING_URL, String.class);
            System.out.println("Keep-alive ping successful");
        } catch (Exception e) {
            System.err.println("Keep-alive ping failed: " + e.getMessage());
        }
    }
}
