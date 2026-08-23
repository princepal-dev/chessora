package org.princeworks.chessora.entity.multiplayer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.princeworks.chessora.entity.game.PlayerColor;
import org.princeworks.chessora.entity.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table (name = "room_participant")
public class RoomParticipant {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "room_id", nullable = false)
    private Room room;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private User user;
    
    private String guestId;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Enumerated(EnumType.STRING)
    private PlayerColor color;
    
    @CreationTimestamp
    private LocalDateTime joinedAt;
}
