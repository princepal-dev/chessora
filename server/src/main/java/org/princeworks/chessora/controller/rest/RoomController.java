package org.princeworks.chessora.controller.rest;

import lombok.RequiredArgsConstructor;
import org.princeworks.chessora.common.CommonResponse;
import org.princeworks.chessora.common.AppConstants;
import org.princeworks.chessora.common.PageResponse;
import org.princeworks.chessora.controller.docs.RoomApi;
import org.princeworks.chessora.entity.user.User;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.response.multiplayer.GetAllRoomCreatedByMeResponse;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.princeworks.chessora.service.multiplayer.IRoomService;
import org.princeworks.chessora.utils.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController implements RoomApi {
  private final AuthUtil authUtil;
  private final IRoomService roomService;

  @Override
  @PostMapping
  public ResponseEntity<CommonResponse<CreateRoomResponse>> createRoom() {
    User user = authUtil.loggedInUser();
    CreateRoomResponse data = roomService.createRoom(user);
    return ResponseEntity.ok(CommonResponse.success("room created successfully!", data));
  }

  @Override
  @GetMapping("/me")
  public ResponseEntity<CommonResponse<List<GetAllRoomCreatedByMeResponse>>> getAllRoomsCreatedByMe(
      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
          Integer pageSize,
      @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
          Integer pageNumber,
      @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false)
          String sortOrder,
      @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY, required = false)
          String sortBy) {
    User loggedInUser = authUtil.loggedInUser();
    
    PageResponse<List<GetAllRoomCreatedByMeResponse>> result =
        roomService.getAllRoomCreatedByMe(loggedInUser, pageNumber, pageSize, sortOrder, sortBy);
    
    return ResponseEntity.ok()
        .body(
            CommonResponse.success(
                "room fetched successfully!", result.getData(), result.getPagination()));
  }

  @Override
  @GetMapping
  public ResponseEntity<CommonResponse<List<GetRoomResponse>>> getAllRooms(
      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
          Integer pageSize,
      @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
          Integer pageNumber,
      @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false)
          String sortOrder,
      @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY, required = false)
          String sortBy) {
    PageResponse<List<GetRoomResponse>> result = roomService.getAllRooms(pageNumber, pageSize, sortOrder, sortBy);
    return ResponseEntity.ok().body(CommonResponse.success("room fetch successfully!", result.getData(), result.getPagination()));
  }
}
