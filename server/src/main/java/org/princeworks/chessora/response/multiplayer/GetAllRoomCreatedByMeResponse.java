package org.princeworks.chessora.response.multiplayer;

import lombok.Data;
import org.princeworks.chessora.entity.multiplayer.RoomStatus;

import java.time.LocalDateTime;

@Data
public class GetAllRoomCreatedByMeResponse {
    private Long roomId;
    private RoomStatus roomStatus;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
}
