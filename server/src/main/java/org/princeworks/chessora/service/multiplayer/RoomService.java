package org.princeworks.chessora.service.multiplayer;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.entity.game.PlayerColor;
import org.princeworks.chessora.entity.multiplayer.Role;
import org.princeworks.chessora.entity.multiplayer.Room;
import org.princeworks.chessora.entity.multiplayer.RoomParticipant;
import org.princeworks.chessora.entity.multiplayer.RoomStatus;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.repositories.RoomRepository;
import org.princeworks.chessora.request.multiplayer.JoinRoomRequest;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.utils.RoomUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
  private final RoomUtil roomUtil;
  private final RoomRepository roomRepository;

  @Override
  public CreateRoomResponse createRoom() {
    int roomCode = roomUtil.generateRoomCode();
    Room room = new Room();
    room.setStatus(RoomStatus.PENDING);
    room.setRoomCode(roomCode);

    roomRepository.save(room);

    return new CreateRoomResponse(roomCode, room.getCreatedAt());
  }

  @Override
  public void joinRoom(Integer roomCode, User user, JoinRoomRequest roomRequest) {
    Room room =
        roomRepository
            .findByRoomCode(roomCode)
            .orElseThrow(
                () -> new RuntimeException("Unable to find the room with room code : " + roomCode));

    if (roomRequest.getIsGuest() && user != null) {
      throw new RuntimeException("You cannot join as a guest and a user at the same time");
    }

    if (!roomRequest.getIsGuest() && user == null) {
      throw new RuntimeException("You have to join as a guest or a verified user");
    }

    if (roomRequest.getRole() == Role.PLAYER && roomRequest.getPlayerColor() == PlayerColor.NONE) {
      throw new RuntimeException("Player color cannot be null, it should be either black or white!");
    }

    if (roomRequest.getRole() == Role.PLAYER && room.getParticipants().stream()
            .filter(participant -> participant.getRole() == Role.PLAYER)
            .count()
        >= 2) {
      throw new RuntimeException("We already have two players in the room");
    }

    String guestId = null;

    boolean whitePlayer =
        room.getParticipants().stream()
            .anyMatch(p -> p.getRole() == Role.PLAYER && p.getColor() == PlayerColor.WHITE);

    boolean blackPlayer =
        room.getParticipants().stream()
            .anyMatch(p -> p.getRole() == Role.PLAYER && p.getColor() == PlayerColor.BLACK);

    if (roomRequest.getIsGuest() && user == null) {
      guestId = roomUtil.generateGuestId();
    }

    if (roomRequest.getRole() == Role.PLAYER
        && roomRequest.getPlayerColor() == PlayerColor.BLACK
        && blackPlayer) {
      throw new RuntimeException("Black player already exists in the room!");
    }

    if (roomRequest.getRole() == Role.PLAYER
        && roomRequest.getPlayerColor() == PlayerColor.WHITE
        && whitePlayer) {
      throw new RuntimeException("White player already exists in the room!");
    }

    RoomParticipant participant = new RoomParticipant();
    participant.setRoom(room);
    participant.setUser(user);
    participant.setGuestId(guestId);

    if (roomRequest.getRole() == Role.SPECTATOR) {
      participant.setRole(Role.SPECTATOR);
      participant.setColor(PlayerColor.NONE);
    } else {
      participant.setRole(Role.PLAYER);
      participant.setColor(roomRequest.getPlayerColor());
    }

    room.addParticipant(participant);

    roomRepository.save(room);
  }
}
