package org.princeworks.chessora.security;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.security.jwt.AuthEntryPointJwt;
import org.princeworks.chessora.security.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final AuthTokenFilter authTokenFilter;
  private final AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // adding cors
    http.cors(Customizer.withDefaults());

    // Adding csrf
    http.csrf(AbstractHttpConfigurer::disable);

    // Disable frame options security for h2 DB
    http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    // Make session stateless
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // Securing end points
    http.authorizeHttpRequests(
        req -> {
          req.requestMatchers("/h2-console/**","/api/v1/auth/**", "/errors").permitAll();
          req.anyRequest().authenticated();
        });

    // Exception Handler
    http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

    // Adding our custom filter chain
    http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
