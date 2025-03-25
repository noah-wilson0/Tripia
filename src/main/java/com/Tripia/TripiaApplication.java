package com.Tripia;

import com.Tripia.tourapi.TourApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TourApiProperties.class)
@EnableScheduling
public class TripiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripiaApplication.class, args);
	}

}
