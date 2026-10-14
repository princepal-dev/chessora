package org.princeworks.chessora.controller.rest;

import org.princeworks.chessora.common.CommonResponse;
import org.princeworks.chessora.controller.docs.HealthApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HeathController implements HealthApi {
  @Override
  @GetMapping
  public ResponseEntity<CommonResponse<Void>> healthCheck() {
    return ResponseEntity.ok(CommonResponse.success("Server is running fine!"));
  }
}
