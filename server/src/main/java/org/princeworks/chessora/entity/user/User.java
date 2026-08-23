package org.princeworks.chessora.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Email
  @NotBlank
  @Column(unique = true)
  @Size(max = 50, message = "email cannot be this big")
  private String email;

  @NotBlank private String fullName;

  @Size(min = 3, max = 30, message = "Username should be in range of 3 to 30")
  private String userName;

  @NotBlank private String password;

  @Enumerated(EnumType.STRING)
  private SignInMethod method;

  private Boolean emailVerified = false;

  public User(
      String email, String fullName, String userName, String password, SignInMethod method) {
    this.email = email;
    this.fullName = fullName;
    this.userName = userName;
    this.password = password;
    this.method = method;
  }

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;
}
