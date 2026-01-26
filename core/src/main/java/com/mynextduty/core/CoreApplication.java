package com.mynextduty.core;

import com.mynextduty.core.dto.location.UpdateLocationRequestDto;
import com.mynextduty.core.service.LocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class CoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoreApplication.class, args);
  }

    @Bean
    public CommandLineRunner setLocationRunner(LocationService locationService) {
        return args -> {
            UpdateLocationRequestDto dto = new UpdateLocationRequestDto();
            dto.setLatitude( 12.9750);
            dto.setLongitude(77.5980);
            //locationService.updateUserLocation(1L,dto);
            locationService.getNearByUsers(2L);
        };
    }
}
