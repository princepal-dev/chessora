package org.princeworks.chessora.service.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
  @Value("${spring.email.verification-url}")
  private String verificationUrl;

  private final JavaMailSender javaMailSender;

  public void sendWelcomeEmail(String email, String fullName, String token) {
      String verificationLink = verificationUrl + token;

      SimpleMailMessage message = new SimpleMailMessage();

      message.setTo(email);
      message.setSubject("Welcome to Chessora");

      message.setText(
          """
            Hi %s,

            Welcome to Chessora!

            We're excited to have you with us.

            Please verify your email address by clicking the link below:

            %s

            This link is used to verify your email address.

            Best regards,
            Chessora Team
            """
              .formatted(fullName, verificationLink));

      javaMailSender.send(message);
  }
}
