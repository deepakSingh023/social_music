package com.example.social_music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SocialMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMusicApplication.class, args);
	}

}
