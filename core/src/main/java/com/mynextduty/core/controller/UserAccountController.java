package com.mynextduty.core.controller;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.ResponseDto;
import com.mynextduty.core.dto.SuccessResponseDto;
import com.mynextduty.core.dto.auth.RegisterRequestDto;
import com.mynextduty.core.service.impl.UserAccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAccountController {
  private final UserAccountService userAccountService;

  @PostMapping("/register")
  public ResponseDto<GlobalMessageDTO> register(
      @Valid @RequestBody RegisterRequestDto registerDto, HttpServletResponse response) {
    return new SuccessResponseDto<>(userAccountService.register(registerDto, response));
  }
}
