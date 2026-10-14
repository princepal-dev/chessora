package org.princeworks.chessora.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.princeworks.chessora.common.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface HealthApi {
  @Operation(summary = "Health Check", description = "Checks the health status of the server.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Server is running fine!"),
    @ApiResponse(responseCode = "500", description = "Server is down or not responding")
  })
  ResponseEntity<CommonResponse<Void>> healthCheck();
}
