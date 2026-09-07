package org.princeworks.chessora.controller.rest;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.common.CommonResponse;
import org.princeworks.chessora.common.AppConstants;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.princeworks.chessora.service.multiplayer.IRoomService;
import org.princeworks.chessora.utils.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {
  private final AuthUtil authUtil;
  private final IRoomService roomService;

  @PostMapping
  public ResponseEntity<CommonResponse<CreateRoomResponse>> createRoom() {
    User user = authUtil.loggedInUser();
    CreateRoomResponse data = roomService.createRoom(user);
    return ResponseEntity.ok(CommonResponse.success("room created successfully!", data));
  }

  @GetMapping("/me")
  public ResponseEntity<CommonResponse<List<GetRoomResponse>>> getAllRoomsCreatedByMe(
      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
          Integer pageSize,
      @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
          Integer pageNumber,
      @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false)
          String sortOrder) {
    User loggedInUser = authUtil.loggedInUser();
    List<GetRoomResponse> data = roomService.getAllRoomCreatedByMe(loggedInUser, pageNumber, pageSize, sortOrder);
    return ResponseEntity.ok().body(CommonResponse.success("room fetched successfully!", data));
  }
  
  @GetMapping
  public ResponseEntity<CommonResponse<List<GetRoomResponse>>> getAllRooms(
          @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
          Integer pageSize,
          @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
          Integer pageNumber,
          @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false)
          String sortOrder
  ) {
    List<GetRoomResponse> data = roomService.getAllRooms(pageNumber, pageSize, sortOrder);
    return ResponseEntity.ok().body(CommonResponse.success("room fetch successfully!", data));
  }
}
