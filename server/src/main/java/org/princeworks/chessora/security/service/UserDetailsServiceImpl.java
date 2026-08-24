package org.princeworks.chessora.security.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUserName(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username : " + username));
    return UserDetailsImpl.build(user);
  }
}
