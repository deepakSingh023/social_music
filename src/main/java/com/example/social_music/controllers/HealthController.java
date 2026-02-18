package com.example.social_music.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class HealthController {

    @GetMapping("/health")
    public String root() {
        return "Social Music Service Running";
    }
}
