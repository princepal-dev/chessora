package org.princeworks.chessora.controller.ws;

import org.princeworks.chessora.request.multiplayer.JoinRoomRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class RoomWsController {
    @MessageMapping("/room/join")
    public void joinRoom(JoinRoomRequest request, Principal principal) {
        
    }
}
