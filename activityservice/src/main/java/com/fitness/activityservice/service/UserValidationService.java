package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId) {
        try {
            log.info("Validating user with ID: {}", userId);
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException e) {
          if(e.getStatusCode() == HttpStatus.NOT_FOUND)
              throw new RuntimeException("User not found with id: " + userId);
          else if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
              throw new RuntimeException("Invalid user ID format: " + userId);
          else
              throw new RuntimeException("Error validating user: " + e.getMessage());
        }
    }
}