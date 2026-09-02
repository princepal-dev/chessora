package org.princeworks.chessora.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.princeworks.chessora.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionId = 1L;

  private Long id;
  private String email;
  private String userName;
  private Boolean emailVerified;

  @JsonIgnore private String password;

  public UserDetailsImpl(Long id, String email, String userName, String password, Boolean emailVerified) {
    this.id = id;
    this.email = email;
    this.userName = userName;
    this.password = password;
    this.emailVerified = emailVerified;
  }

  public static UserDetailsImpl build(User user) {
    return new UserDetailsImpl(
        user.getId(), user.getEmail(), user.getUserName(), user.getPassword(), user.getEmailVerified());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public @Nullable String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }
}
