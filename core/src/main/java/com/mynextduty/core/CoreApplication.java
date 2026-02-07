package com.mynextduty.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CoreApplication {
  public static void main(String[] args) {
    SpringApplication.run(CoreApplication.class, args);
  }


//    @Bean
//    public CommandLineRunner setLocationRunner(LocationService locationService) {
//        return args -> {
//            UpdateLocationRequestDto dto = new UpdateLocationRequestDto();
//            dto.setLatitude( 12.9750);
//            dto.setLongitude(77.5980);
//            //locationService.updateUserLocation(1L,dto);
//            locationService.getNearByUsers(2L);
//        };
//    }

}
