package org.princeworks.chessora.response.multiplayer;

import lombok.Data;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.entity.multiplayer.RoomStatus;

import java.time.LocalDateTime;

@Data
public class GetRoomResponse {
    private Long roomId;
    private User creator;
    private RoomStatus roomStatus;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
}
