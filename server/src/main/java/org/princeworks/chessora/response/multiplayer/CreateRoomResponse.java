package org.princeworks.chessora.response.multiplayer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateRoomResponse {
    private Integer roomCode;
    private LocalDateTime createdAt;

    public CreateRoomResponse(Integer roomCode, LocalDateTime createdAt) {
        this.roomCode = roomCode;
        this.createdAt = createdAt;
    }
}
