package com.mynextduty.core.service.impl;

import com.mynextduty.core.dto.location.NearbyUserDTO;
import com.mynextduty.core.dto.location.UpdateLocationRequest;
import com.mynextduty.core.entity.User;
import com.mynextduty.core.entity.UserLocation;
import com.mynextduty.core.enums.LifeStage;
import com.mynextduty.core.repository.UserLocationRepository;
import com.mynextduty.core.service.LocationService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final UserLocationRepository repo;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public LocationServiceImpl(UserLocationRepository repo) {
        this.repo = repo;
    }

    @Override
    public void updateLocation(User user, UpdateLocationRequest req) {


        Point jtsPoint = geometryFactory.createPoint(
                new Coordinate(req.longitude, req.latitude)
        );

        UserLocation loc = repo.findByUserId(user.getId())
                .orElse(UserLocation.builder()
                        .user(user)
                        .build());

        loc.setLocation(jtsPoint);
        repo.save(loc);
    }

    @Override
    public List<NearbyUserDTO> findNearby(
            double lat,
            double lng,
            int radius
    ) {
        return repo.findNearbyUsersWithLocation(lat, lng, radius)
                .stream()
                .map(r -> {
                    NearbyUserDTO dto = new NearbyUserDTO();
                    dto.id = ((Number) r[0]).longValue();
                    dto.firstName = (String) r[1];
                    dto.lastName = (String) r[2];
                    dto.email = (String) r[3];
                    dto.currentOccupation = (String) r[4];
                    dto.lifeStage = LifeStage.valueOf((String) r[5]);
                    dto.lat = ((Number) r[6]).doubleValue();
                    dto.lng = ((Number) r[7]).doubleValue();
                    return dto;
                })
                .toList();
    }
}
