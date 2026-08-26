package org.princeworks.chessora.controller.rest;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.common.ApiResponse;
import org.princeworks.chessora.entity.user.SignInMethod;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.repositories.UserRepository;
import org.princeworks.chessora.request.user.SignInRequest;
import org.princeworks.chessora.request.user.SignUpRequest;
import org.princeworks.chessora.response.user.SignInResponse;
import org.princeworks.chessora.security.jwt.JwtUtils;
import org.princeworks.chessora.security.service.UserDetailsImpl;
import org.princeworks.chessora.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final JwtUtils jwtUtils;
  private final IUserService userService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
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
          .body(ApiResponse.error("Error : either email or username is already registered"));

    String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
    String fullName = signUpRequest.getFirstName() + signUpRequest.getLastName();
    User user =
        new User(
            signUpRequest.getEmail(),
            fullName,
            signUpRequest.getUsername(),
            hashedPassword,
            SignInMethod.EMAIL);
    userRepository.save(user);

    return new ResponseEntity<>(
        ApiResponse.success("User registered successfully!"), HttpStatus.CREATED);
  }
}
