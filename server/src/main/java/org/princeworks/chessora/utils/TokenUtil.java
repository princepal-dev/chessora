package org.princeworks.chessora.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {
    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }
}
