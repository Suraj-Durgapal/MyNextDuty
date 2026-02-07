package com.mynextduty.core.controller;

import com.mynextduty.core.dto.GlobalMessageDto;
import com.mynextduty.core.dto.ResponseDto;
import com.mynextduty.core.dto.SuccessResponseDto;
import com.mynextduty.core.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

@RequiredArgsConstructor
public class UserAccountController {
  private final UserAccountService userAccountService;


  @PostMapping("/verify-email")
  public ResponseDto<GlobalMessageDto> verifyEmail(@RequestParam("token") String token) {
    return new SuccessResponseDto<>(userAccountService.verifyEmail(token));
  }

  @PostMapping("/verify")
  public ResponseDto<GlobalMessageDto> verify() {
    return new SuccessResponseDto<>(userAccountService.verify());
  }
}
