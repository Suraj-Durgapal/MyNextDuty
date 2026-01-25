package com.mynextduty.core.controller;

import com.mynextduty.core.dto.location.NearbyUserDTO;
import com.mynextduty.core.dto.location.UpdateLocationRequest;
import com.mynextduty.core.entity.User;
import com.mynextduty.core.service.LocationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/update")
    public void updateLocation(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateLocationRequest req
    ) {
        locationService.updateLocation(user, req);
    }

    @GetMapping("/nearby")
    public List<NearbyUserDTO> nearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam int radius
    ) {
        return locationService.findNearby(lat, lng, radius);
    }
}
