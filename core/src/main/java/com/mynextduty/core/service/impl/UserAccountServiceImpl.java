package com.mynextduty.core.service.impl;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.auth.RegisterRequestDto;
import com.mynextduty.core.entity.User;
import com.mynextduty.core.exception.GenericApplicationException;
import com.mynextduty.core.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {
  private final UserRepository userRepository;

  @Override
  @Transactional
  public GlobalMessageDTO register(
      RegisterRequestDto registerRequestDto, HttpServletResponse httpServletResponse) {
    if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
      throw new GenericApplicationException("User already exists.", 409);
    }
    User user =
        User.builder()
            .email(registerRequestDto.getEmail())
            .password(registerRequestDto.getPassword())
            .firstName(registerRequestDto.getFirstName())
            .lastName(registerRequestDto.getLastName())
            .isVerified(false)
            .build();
    userRepository.save(user);
    log.info("New user registered with email: {}", registerRequestDto.getEmail());
    return GlobalMessageDTO.builder().message("User registered successfully").build();
  }
}
