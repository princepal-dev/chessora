package org.princeworks.chessora.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class PasswordUtil {
  private final PasswordEncoder passwordEncoder;

  public String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  public boolean isPasswordSecure(String password) {
    if (password.length() < 8) {
      return true;
    }
    boolean hasUppercase = !password.equals(password.toLowerCase());
    boolean hasLowercase = !password.equals(password.toUpperCase());
    boolean hasDigit = password.matches(".*\\d.*");
    boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

    return !hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar;
  }
}
