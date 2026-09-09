package org.princeworks.chessora.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.princeworks.chessora.common.CommonResponse;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomApi {

    @Operation(
            summary = "Create a room",
            description = "Creates a new multiplayer chess room for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room created successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "400", description = "Unable to create room")
    })
    ResponseEntity<CommonResponse<CreateRoomResponse>> createRoom();


    @Operation(
            summary = "Get my rooms",
            description = "Retrieves all rooms created by the currently authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rooms fetched successfully"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    ResponseEntity<CommonResponse<List<GetRoomResponse>>> getAllRoomsCreatedByMe(
            @Parameter(
                    description = "Number of rooms to return per page",
                    example = "10")
            Integer pageSize,

            @Parameter(
                    description = "Page number to retrieve",
                    example = "0")
            Integer pageNumber,

            @Parameter(
                    description = "Sort direction",
                    example = "desc")
            String sortOrder);


    @Operation(
            summary = "Get all rooms",
            description = "Retrieves all available multiplayer chess rooms.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rooms fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    ResponseEntity<CommonResponse<List<GetRoomResponse>>> getAllRooms(
            @Parameter(
                    description = "Number of rooms to return per page",
                    example = "10")
            Integer pageSize,

            @Parameter(
                    description = "Page number to retrieve",
                    example = "0")
            Integer pageNumber,

            @Parameter(
                    description = "Sort direction",
                    example = "desc")
            String sortOrder);
}