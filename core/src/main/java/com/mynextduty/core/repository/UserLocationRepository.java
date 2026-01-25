package com.mynextduty.core.repository;

import com.mynextduty.core.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository
        extends JpaRepository<UserLocation, Long> {

    @Query(value = """
    SELECT 
      u.id,
      u.first_name,
      u.last_name,
      u.email,
      u.current_occupation,
      u.life_stage,
      ST_Y(ul.location::geometry) AS lat,
      ST_X(ul.location::geometry) AS lng
    FROM user_locations ul
    JOIN users u ON u.id = ul.user_id
    WHERE ST_DWithin(
      ul.location,
      ST_MakePoint(:lng, :lat)::geography,
      :radius * 1000
    )
    """, nativeQuery = true)
    List<Object[]> findNearbyUsersWithLocation(
            double lat,
            double lng,
            int radius
    );

    Optional<UserLocation> findByUserId(Long userId);
}
