package com.mynextduty.core.service.impl;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.auth.RegisterRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserAccountService {
  GlobalMessageDTO register(
      RegisterRequestDto registerRequestDto, HttpServletResponse httpServletResponse);
}
