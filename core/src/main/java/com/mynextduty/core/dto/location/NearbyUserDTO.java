package com.mynextduty.core.dto.location;

import com.mynextduty.core.enums.LifeStage;

public class NearbyUserDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String currentOccupation;
    public LifeStage lifeStage;
    public double lat;
    public double lng;
}
