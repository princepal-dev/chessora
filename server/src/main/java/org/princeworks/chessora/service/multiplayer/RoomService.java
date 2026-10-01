package org.princeworks.chessora.service.multiplayer;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.common.PageResponse;
import org.princeworks.chessora.common.PaginationData;
import org.princeworks.chessora.entity.multiplayer.Room;
import org.princeworks.chessora.entity.multiplayer.RoomStatus;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.repositories.RoomRepository;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.response.multiplayer.GetAllRoomCreatedByMeResponse;
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
  public GetRoomResponse getRoomById(Long id) {
    Room room =
        roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    GetRoomResponse response = new GetRoomResponse();
    response.setRoomId(room.getId());
    response.setRoomStatus(room.getStatus());
    response.setCreator(room.getRoomCreator());
    response.setStartedAt(room.getStartedAt());
    response.setCreatedAt(room.getCreatedAt());
    return response;
  }

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
  @Transactional
  public PageResponse<List<GetAllRoomCreatedByMeResponse>> getAllRoomCreatedByMe(
      User loggedInUser, Integer pageNumber, Integer pageSize, String sortOrder, String sortBy) {
    Sort sortByAndOrder =
        sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Room> roomPage = roomRepository.findByRoomCreator(pageDetails, loggedInUser);

    List<Room> roomsCreatedByUser = roomPage.getContent();

    if (roomsCreatedByUser.isEmpty()) throw new RuntimeException("No rooms created by you");

    List<GetAllRoomCreatedByMeResponse> rooms =
        roomsCreatedByUser.stream()
            .map(
                item -> {
                  GetAllRoomCreatedByMeResponse room = new GetAllRoomCreatedByMeResponse();
                  room.setRoomId(item.getId());
                  room.setRoomStatus(item.getStatus());
                  room.setCreatedAt(item.getCreatedAt());
                  room.setStartedAt(item.getStartedAt());
                  return room;
                })
            .toList();

    PaginationData pagination =
        new PaginationData(
            roomPage.getNumber(),
            roomPage.getSize(),
            roomPage.getTotalElements(),
            roomPage.getTotalPages(),
            roomPage.hasNext(),
            roomPage.hasPrevious(),
            roomPage.isLast());

    return new PageResponse<>(rooms, pagination);
  }

  @Override
  @Transactional
  public PageResponse<List<GetRoomResponse>> getAllRooms(
      Integer pageNumber, Integer pageSize, String sortOrder, String sortBy) {
    Sort sortByAndOrder =
        sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Room> roomPage = roomRepository.findAll(pageDetails);

    List<Room> allRooms = roomPage.getContent();
    if (allRooms.isEmpty()) throw new RuntimeException("No active rooms available");

    List<GetRoomResponse> rooms =
        allRooms.stream()
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

    PaginationData pagination =
        new PaginationData(
            roomPage.getNumber(),
            roomPage.getSize(),
            roomPage.getTotalElements(),
            roomPage.getTotalPages(),
            roomPage.hasNext(),
            roomPage.hasPrevious(),
            roomPage.isLast());

    return new PageResponse<>(rooms, pagination);
  }
}
