package com.mynextduty.core.config.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynextduty.core.dto.auth.CustomUserDetails;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleBasedRateLimiterFilter extends OncePerRequestFilter {

  private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();
  private final ObjectMapper objectMapper;
  private final RateLimitProperties properties;

  /**
   * Filters incoming requests and applies rate limiting based on user roles.
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param filterChain FilterChain
   * @throws ServletException ServletException
   * @throws IOException IOException
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null
        && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
      String username = userDetails.getUsername();

      Bucket bucket = userBuckets.computeIfAbsent(username, key -> createNewBucket(userDetails));

      if (bucket.tryConsume(1)) {
        filterChain.doFilter(request, response);
        return;
      } else {
        writeRateLimitExceededResponse(response);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Creates a new bucket with a rate limit based on the user's role.
   *
   * @param userDetails CustomUserDetails
   * @return Bucket
   */
  private Bucket createNewBucket(CustomUserDetails userDetails) {
    int capacity = properties.getDefaultLimit();
    if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      capacity = properties.getLimitForRole("ADMIN");
      log.debug("Setting ADMIN rate limit capacity: {}", capacity);
    } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
      capacity = properties.getLimitForRole("CUSTOMER");
      log.debug("Setting CUSTOMER rate limit capacity: {}", capacity);
    }
    return Bucket.builder()
        .addLimit(
            Bandwidth.classic(
                capacity,
                Refill.intervally(
                    properties.getRefillTokens(),
                    Duration.ofSeconds(properties.getRefillDuration()))))
        .build();
  }

  /**
   * Writes a response indicating that the rate limit has been exceeded.
   *
   * @param response HttpServletResponse
   * @throws IOException IOException
   */
  private void writeRateLimitExceededResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setHeader("X-Rate-Limit-Retry-After", String.valueOf(properties.getRefillDuration()));
    Map<String, Object> errorResponse =
        Map.of(
            "status", "TOO_MANY_REQUESTS",
            "code", HttpStatus.TOO_MANY_REQUESTS.value(),
            "message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
            "retryAfterSeconds", properties.getRefillDuration(),
            "timestamp", System.currentTimeMillis());
    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return path.startsWith("/auth");
  }
}
