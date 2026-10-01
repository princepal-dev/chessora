package org.princeworks.chessora.entity.user;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @JsonIgnore
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

  @JsonIgnore @NotBlank private String password;

  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private SignInMethod method;

  @JsonIgnore private Boolean emailVerified = false;

  public User(
      String email, String fullName, String userName, String password, SignInMethod method) {
    this.email = email;
    this.fullName = fullName;
    this.userName = userName;
    this.password = password;
    this.method = method;
  }

  @JsonIgnore @CreationTimestamp private LocalDateTime createdAt;

  @JsonIgnore @UpdateTimestamp private LocalDateTime updatedAt;
}
