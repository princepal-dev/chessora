package org.princeworks.chessora.service.multiplayer;

import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.request.multiplayer.JoinRoomRequest;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;

public interface IRoomService {
    CreateRoomResponse createRoom();
    void joinRoom(Integer roomCode, User user, JoinRoomRequest roomRequest);
}
