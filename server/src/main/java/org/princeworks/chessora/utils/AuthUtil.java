package org.princeworks.chessora.utils;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
  private final UserRepository userRepository;

  public String loggedInEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user =
        userRepository
            .findByUserName(authentication.getName())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "User not found with username: " + authentication.getName()));
    return user.getEmail();
  }

  public Long loggedInUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user =
        userRepository
            .findByUserName(authentication.getName())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "User not found with username: " + authentication.getName()));
    return user.getId();
  }

  public User loggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository
        .findByUserName(authentication.getName())
        .orElseThrow(
            () ->
                new RuntimeException("User not found with username: " + authentication.getName()));
  }
}
