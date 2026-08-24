package org.princeworks.chessora.entity.multiplayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.princeworks.chessora.entity.game.Game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(unique = true, nullable = false)
  private Integer roomCode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoomStatus status;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id")
  private Game game;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  List<RoomParticipant> participants = new ArrayList<>();

  @CreationTimestamp private LocalDateTime createdAt;

  private LocalDateTime startedAt;

  private LocalDateTime endAt;

  public void addParticipant(RoomParticipant participant) {
    participants.add(participant);
    participant.setRoom(this);
  }

  public void removeParticipant(RoomParticipant participant) {
    participants.remove(participant);
    participant.setRoom(null);
  }
}
