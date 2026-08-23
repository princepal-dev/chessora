package org.princeworks.chessora.controller;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.common.ApiResponse;
import org.princeworks.chessora.request.multiplayer.JoinRoomRequest;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.service.multiplayer.IRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {
  private final IRoomService roomService;

  @GetMapping
  public ResponseEntity<ApiResponse<CreateRoomResponse>> createRoom() {
    CreateRoomResponse data = roomService.createRoom();
    return ResponseEntity.ok(ApiResponse.success("room created successfully!", data));
  }

  @PutMapping("/{roomCode}")
  public ResponseEntity<ApiResponse<Void>> joinRoom(
      @PathVariable Integer roomCode, @RequestBody JoinRoomRequest roomRequest) {
    // TODO : We have to add user here
    roomService.joinRoom(roomCode, null, roomRequest);
    return ResponseEntity.ok(ApiResponse.success("room joined successfully!"));
  }
  
  
}
