package com.mynextduty.core;

import com.mynextduty.core.dto.location.UpdateLocationRequest;
import com.mynextduty.core.repository.UserLocationRepository;
import com.mynextduty.core.service.LocationService;
import com.mynextduty.core.service.impl.LocationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class CoreApplication {

 public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		}

@Bean
public CommandLineRunner setLocationRunner(LocationService locationService) {
		return args -> {

		UpdateLocationRequest requestDto = new UpdateLocationRequest();
		requestDto.setLatitude(12.9716);
		requestDto.setLongitude(77.5946);

		locationService.updateLocation(requestDto);
		};
		}


}
