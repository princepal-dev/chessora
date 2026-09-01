package org.princeworks.chessora.controller.rest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.common.ApiResponse;
import org.princeworks.chessora.entity.user.SignInMethod;
import org.princeworks.chessora.entity.user.TokenType;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.entity.user.VerificationToken;
import org.princeworks.chessora.repositories.TokenRepository;
import org.princeworks.chessora.repositories.UserRepository;
import org.princeworks.chessora.request.user.ForgotPasswordRequest;
import org.princeworks.chessora.request.user.PasswordResetRequest;
import org.princeworks.chessora.request.user.SignInRequest;
import org.princeworks.chessora.request.user.SignUpRequest;
import org.princeworks.chessora.response.user.SignInResponse;
import org.princeworks.chessora.security.jwt.JwtUtils;
import org.princeworks.chessora.security.service.UserDetailsImpl;
import org.princeworks.chessora.service.email.EmailService;
import org.princeworks.chessora.utils.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final JwtUtils jwtUtils;
  private final TokenUtil tokenUtil;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenRepository tokenRepository;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<SignInResponse>> authenticateUser(
      @RequestBody SignInRequest signInRequest) {
    Authentication authentication;

    try {
      authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  signInRequest.getUsername(), signInRequest.getPassword()));
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("Bad Credentials"));
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    if (userDetails == null)
      return ResponseEntity.badRequest().body(ApiResponse.error("Error : in signing in!"));

    String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);

    return new ResponseEntity<>(
        ApiResponse.success(
            "Sign in success", new SignInResponse(jwtToken, userDetails.getUsername())),
        HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<Void>> registerUser(@RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())
        || userRepository.existsByUserName(signUpRequest.getUsername()))
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Email or username is already registered"));

    String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
    String fullName = signUpRequest.getFirstName();

    if (signUpRequest.getLastName() != null && !signUpRequest.getLastName().isBlank()) {
      fullName += " " + signUpRequest.getLastName();
    }

    User user =
        new User(
            signUpRequest.getEmail(),
            fullName,
            signUpRequest.getUsername(),
            hashedPassword,
            SignInMethod.EMAIL);
    userRepository.save(user);

    VerificationToken token = new VerificationToken();
    token.setToken(tokenUtil.generateVerificationToken());
    token.setType(TokenType.EMAIL_VERIFICATION);
    token.setUser(user);
    token.setExpiry(Instant.now().plus(1, ChronoUnit.DAYS));

    emailService.sendWelcomeEmail(signUpRequest.getEmail(), fullName, token.getToken());

    tokenRepository.save(token);

    return new ResponseEntity<>(
        ApiResponse.success("User registered successfully!"), HttpStatus.CREATED);
  }

  @Transactional
  @GetMapping("/verify-email/{token}")
  public ResponseEntity<ApiResponse<Void>> verifyEmail(@PathVariable String token) {
    if (token == null || token.isBlank())
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Email verification token is null or empty!"));

    VerificationToken savedToken =
        tokenRepository
            .findByToken(token)
            .orElseThrow(() -> new RuntimeException("Unable to find the verification token"));

    if (savedToken.getType() != TokenType.EMAIL_VERIFICATION)
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Verification token type mismatch"));

    if (savedToken.isUsed())
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Verification token is already used"));

    if (savedToken.getExpiry().isBefore(Instant.now()))
      return ResponseEntity.badRequest().body(ApiResponse.error("Verification token is expired"));

    User user = savedToken.getUser();
    user.setEmailVerified(true);
    savedToken.setUsed(true);

    userRepository.save(user);
    tokenRepository.save(savedToken);

    return ResponseEntity.ok().body(ApiResponse.success("Email verified successfully"));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ApiResponse<Void>> forgotPassword(
      @RequestBody ForgotPasswordRequest passwordResetRequest) {
    if (!userRepository.existsByEmail(passwordResetRequest.getEmail()))
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Email not registered with chessora!"));

    User user =
        userRepository
            .findByEmail(passwordResetRequest.getEmail())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Unable to find the user with email : " + passwordResetRequest.getEmail()));
    
    if (!user.getEmailVerified())
      return ResponseEntity.badRequest()
              .body(ApiResponse.error("Please verify your email to reset your password!"));

    VerificationToken token = new VerificationToken();
    token.setUser(user);
    token.setToken(tokenUtil.generateVerificationToken());
    token.setType(TokenType.PASSWORD_RESET);
    token.setExpiry(Instant.now().plus(1, ChronoUnit.HOURS));

    emailService.sendPasswordResetToken(user.getEmail(), user.getFullName(), token.getToken());

    tokenRepository.save(token);

    return ResponseEntity.ok().body(ApiResponse.success("Password reset token sent successfully!"));
  }

  @Transactional
  @PostMapping("/password-reset/{token}")
  public ResponseEntity<ApiResponse<Void>> forgotPassword(
      @PathVariable String token, @RequestBody PasswordResetRequest passwordResetRequest) {
    if (token == null || token.isBlank())
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Password reset token is null or empty!"));

    VerificationToken savedToken =
        tokenRepository
            .findByToken(token)
            .orElseThrow(() -> new RuntimeException("Unable to find the verification token"));

    if (savedToken.getType() != TokenType.PASSWORD_RESET)
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Verification token type mismatch"));

    if (savedToken.isUsed())
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("Verification token is already used"));

    if (savedToken.getExpiry().isBefore(Instant.now()))
      return ResponseEntity.badRequest().body(ApiResponse.error("Verification token is expired"));

    User user = savedToken.getUser();
    String hashedPassword = passwordEncoder.encode(passwordResetRequest.getPassword());
    user.setPassword(hashedPassword);

    savedToken.setUsed(true);

    userRepository.save(user);
    tokenRepository.save(savedToken);

    return new ResponseEntity<>(ApiResponse.success("Password reset successfully!"), HttpStatus.OK);
  }
}
