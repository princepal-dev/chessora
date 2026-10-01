package org.princeworks.chessora.service.multiplayer;

import org.princeworks.chessora.common.PageResponse;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.response.multiplayer.GetAllRoomCreatedByMeResponse;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;

import java.util.List;

public interface IRoomService {
  GetRoomResponse getRoomById(Long id);
  CreateRoomResponse createRoom(User user);
  PageResponse<List<GetAllRoomCreatedByMeResponse>> getAllRoomCreatedByMe(
      User loggedInUser, Integer pageNumber, Integer pageSize, String sortOrder, String sortBy);
  PageResponse<List<GetRoomResponse>> getAllRooms(
      Integer pageNumber, Integer pageSize, String sortOrder, String sortBy);
}
