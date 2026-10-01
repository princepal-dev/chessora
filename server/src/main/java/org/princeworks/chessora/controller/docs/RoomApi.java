package org.princeworks.chessora.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.princeworks.chessora.common.CommonResponse;
import org.princeworks.chessora.response.multiplayer.CreateRoomResponse;
import org.princeworks.chessora.response.multiplayer.GetAllRoomCreatedByMeResponse;
import org.princeworks.chessora.response.multiplayer.GetRoomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomApi {

    @Operation(
            summary = "Create a room",
            description =
                    "Creates a new multiplayer chess room for the authenticated user.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Room created successfully"),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to create room")
    })
    ResponseEntity<CommonResponse<CreateRoomResponse>> createRoom();


    @Operation(
            summary = "Get my rooms",
            description =
                    "Retrieves rooms created by the currently authenticated user with pagination and sorting.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rooms fetched successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination or sorting parameters"),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated")
    })
    ResponseEntity<CommonResponse<List<GetAllRoomCreatedByMeResponse>>> getAllRoomsCreatedByMe(

            @Parameter(
                    name = "pageSize",
                    description = "Number of rooms to return per page",
                    example = "10",
                    in = ParameterIn.QUERY)
            Integer pageSize,

            @Parameter(
                    name = "pageNumber",
                    description = "Page number to retrieve",
                    example = "0",
                    in = ParameterIn.QUERY)
            Integer pageNumber,

            @Parameter(
                    name = "sortOrder",
                    description = "Sort direction",
                    example = "desc",
                    in = ParameterIn.QUERY)
            String sortOrder,

            @Parameter(
                    name = "sortBy",
                    description = "Field by which the rooms should be sorted",
                    example = "createdAt",
                    in = ParameterIn.QUERY)
            String sortBy);


    @Operation(
            summary = "Get all rooms",
            description =
                    "Retrieves all available multiplayer chess rooms with pagination and sorting.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rooms fetched successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination or sorting parameters")
    })
    ResponseEntity<CommonResponse<List<GetRoomResponse>>> getAllRooms(

            @Parameter(
                    name = "pageSize",
                    description = "Number of rooms to return per page",
                    example = "10",
                    in = ParameterIn.QUERY)
            Integer pageSize,

            @Parameter(
                    name = "pageNumber",
                    description = "Page number to retrieve",
                    example = "0",
                    in = ParameterIn.QUERY)
            Integer pageNumber,

            @Parameter(
                    name = "sortOrder",
                    description = "Sort direction",
                    example = "desc",
                    in = ParameterIn.QUERY)
            String sortOrder,

            @Parameter(
                    name = "sortBy",
                    description = "Field by which the rooms should be sorted",
                    example = "createdAt",
                    in = ParameterIn.QUERY)
            String sortBy);
}