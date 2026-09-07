package org.princeworks.chessora.service.multiplayer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.princeworks.chessora.entity.multiplayer.Room;
import org.princeworks.chessora.entity.multiplayer.RoomStatus;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.repositories.RoomRepository;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.princeworks.chessora.utils.RoomUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
  private final RoomUtil roomUtil;
  private final RoomRepository roomRepository;

  @Override
  public CreateRoomResponse createRoom(User user) {
    int roomCode = roomUtil.generateRoomCode();
    Room room = new Room();
    room.setStatus(RoomStatus.PENDING);
    room.setRoomCode(roomCode);
    room.setRoomCreator(user);

    roomRepository.save(room);

    return new CreateRoomResponse(roomCode, room.getCreatedAt());
  }

  @Override
  public List<GetRoomResponse> getAllRoomCreatedByMe(
      User loggedInUser, Integer pageNumber, Integer pageSize, String sortOrder) {
    Sort sortByAndOrder =
        sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortOrder).ascending()
            : Sort.by(sortOrder).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Room> roomPage = roomRepository.findByRoomCreator(pageDetails, loggedInUser);

    List<Room> roomsCreatedByUser = roomPage.getContent();

    if (roomsCreatedByUser.isEmpty()) throw new RuntimeException("No rooms created by you");

    return getGetRoomResponses(roomsCreatedByUser);
  }

  @Override
  public List<GetRoomResponse> getAllRooms(Integer pageNumber, Integer pageSize, String sortOrder) {
    Sort sortByAndOrder =
        sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortOrder).ascending()
            : Sort.by(sortOrder).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Room> allRoomsPage = roomRepository.findAll(pageDetails);

    List<Room> allRooms = allRoomsPage.getContent();

    if (allRooms.isEmpty()) throw new RuntimeException("No active rooms available");

    return getGetRoomResponses(allRooms);
  }

  @NonNull
  private List<GetRoomResponse> getGetRoomResponses(List<Room> rooms) {
    return rooms.stream()
        .map(
            item -> {
              GetRoomResponse room = new GetRoomResponse();
              room.setRoomId(item.getId());
              room.setRoomStatus(item.getStatus());
              room.setCreator(item.getRoomCreator());
              room.setStartedAt(item.getStartedAt());
              room.setCreatedAt(item.getCreatedAt());
              return room;
            })
        .toList();
  }
}
