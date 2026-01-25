package com.mynextduty.core.service;

import com.mynextduty.core.dto.location.NearbyUserDTO;
import com.mynextduty.core.dto.location.UpdateLocationRequest;
import com.mynextduty.core.entity.User;

import java.util.List;

public interface LocationService {

    void updateLocation(User user, UpdateLocationRequest req);

    List<NearbyUserDTO> findNearby(
            double lat,
            double lng,
            int radius
    );
}
