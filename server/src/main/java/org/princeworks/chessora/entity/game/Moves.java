package org.princeworks.chessora.entity.game;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.princeworks.chessora.entity.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table (name = "moves")
public class Moves {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private Integer moveNumber;
  
  @Enumerated(EnumType.STRING)
  private PlayerColor playerColor;

  @Column(nullable = false, length = 20)
  private String moveNotation;

  // Forsyth–Edwards Notation : It's a standard string that represents the entire 
  // state of a chess board at a particular moment.
  @Column(nullable = false, length = 100)
  private String fen; 

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
