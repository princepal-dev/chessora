package org.princeworks.chessora.controller.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameWsController {
    private final SimpMessagingTemplate messagingTemplate;

    public GameWsController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @MessageMapping("/game/move")
    public void makeMove() {
        
    }
}
