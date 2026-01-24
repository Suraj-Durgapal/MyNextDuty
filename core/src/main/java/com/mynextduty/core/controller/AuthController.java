package com.mynextduty.core.controller;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.ResponseDto;
import com.mynextduty.core.dto.SuccessResponseDto;
import com.mynextduty.core.dto.auth.AuthRequestDto;
import com.mynextduty.core.dto.auth.AuthResponseDto;
import com.mynextduty.core.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @GetMapping("/public-key")
  public ResponseDto<String> publicKey() {
    return new SuccessResponseDto<>(authService.publicKey());
  }

  @PostMapping("/login")
  public ResponseDto<AuthResponseDto> login(
      @Valid @RequestBody AuthRequestDto loginDto, HttpServletResponse response) {
    return new SuccessResponseDto<>(authService.login(loginDto, response));
  }

  @GetMapping("/refresh")
  public ResponseDto<AuthResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse httpServletResponse) {
    return new SuccessResponseDto<>(authService.refreshToken(request,httpServletResponse));
  }

  @PostMapping("/logout")
  public ResponseDto<GlobalMessageDTO> logout(HttpServletRequest request) {
    return new SuccessResponseDto<>(authService.logout(request));
  }

  @PostMapping("/verify-email")
  public ResponseDto<GlobalMessageDTO> verifyEmail(@RequestParam String token) {
    return new SuccessResponseDto<>(authService.verifyEmail(token));
  }
}
