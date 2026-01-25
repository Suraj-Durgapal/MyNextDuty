package com.mynextduty.core.service;

import com.mynextduty.core.dto.SuccessResponseDto;
import com.mynextduty.core.dto.location.UpdateLocationRequestDto;
import com.mynextduty.core.dto.user.UserResponseDto;
import java.util.List;

public interface LocationService {

  SuccessResponseDto<UserResponseDto> updateUserLocation(Long userId, UpdateLocationRequestDto req);

  SuccessResponseDto<List<UserResponseDto>> getNearByUsers(Long userId);
}
