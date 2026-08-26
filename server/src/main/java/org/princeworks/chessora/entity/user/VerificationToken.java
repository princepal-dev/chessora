package org.princeworks.chessora.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class VerificationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TokenType type;

  private boolean used = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private Instant expiry;

  @CreationTimestamp private LocalDateTime createdAt;

  public VerificationToken(String token, TokenType type, boolean used, User user, Instant expiry) {
    this.token = token;
    this.type = type;
    this.used = used;
    this.user = user;
    this.expiry = expiry;
  }
}
