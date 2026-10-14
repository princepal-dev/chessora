package org.princeworks.chessora.controller.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.princeworks.chessora.request.multiplayer.JoinRoomRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RoomWsController {
    private final SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/room.send")
    public void joinRoom(JoinRoomRequest request, Principal principal) {
        String userName = principal.getName();
        messagingTemplate.convertAndSend("/topic/room", request);
    }
}
