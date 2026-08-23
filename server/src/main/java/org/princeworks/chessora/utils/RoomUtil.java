package org.princeworks.chessora.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoomUtil {
    public int generateRoomCode() {
        return (int) (Math.random() * 1000);
    }
    
    public String generateGuestId() {
        return generateRandomString(6);
    }
    
    private String generateRandomString(int size) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, size);
    }
}
