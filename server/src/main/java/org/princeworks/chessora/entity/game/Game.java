package org.princeworks.chessora.entity.game;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.princeworks.chessora.entity.multiplayer.Room;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne (mappedBy = "game", cascade = CascadeType.ALL)
    private Room room;
    
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private LocalDateTime endedAt;
}
