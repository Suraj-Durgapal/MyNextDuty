package com.mynextduty.core.repository;

import com.mynextduty.core.dto.projection.NearbyUserLocation;
import com.mynextduty.core.dto.projection.NearbyUserProjection;
import com.mynextduty.core.entity.UserLocation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

  @Query(
          value = """
    SELECT u.id as userId,
           u.first_name as firstname,
           u.last_name as lastname,
           u.email as email,
           ST_AsText(ul.location) as location
    FROM user_locations ul
    JOIN users u ON u.id = ul.user_id
    WHERE ul.user_id <> :userId
      AND ST_DWithin(
          ul.location,
          (SELECT location FROM user_locations WHERE user_id = :userId),
          :radiusMeters
      )
    """,
          nativeQuery = true)
  List<NearbyUserLocation> findNearbyUserLocations(
          @Param("userId") Long userId,
          @Param("radiusMeters") double radiusMeters);


  @Query(
      value =
          """
      SELECT ul.user_id AS userId,
             ST_Distance(
                 ul.location,
                 (SELECT location FROM user_locations WHERE user_id = :userId)
             ) AS distance
      FROM user_locations ul
      WHERE ul.user_id <> :userId
        AND ST_DWithin(
            ul.location,
            (SELECT location FROM user_locations WHERE user_id = :userId),
            :radiusMeters
        )
      ORDER BY distance
      LIMIT :limit
      """,
      nativeQuery = true)
  List<NearbyUserProjection> findNearbyUserLocationsSorted(
      @Param("userId") Long userId,
      @Param("radiusMeters") double radiusMeters,
      @Param("limit") int limit);

  Optional<UserLocation> findByUserId(Long userId);
}
