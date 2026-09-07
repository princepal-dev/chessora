package org.princeworks.chessora.service.multiplayer;

import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;

import java.util.List;

public interface IRoomService {
  CreateRoomResponse createRoom(User user);

  List<GetRoomResponse> getAllRoomCreatedByMe(
      User loggedInUser, Integer pageNumber, Integer pageSize, String sortOrder);

  List<GetRoomResponse> getAllRooms(Integer pageNumber, Integer pageSize, String sortOrder);
}
