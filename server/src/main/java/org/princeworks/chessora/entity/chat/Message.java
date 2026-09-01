package org.princeworks.chessora.entity.chat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String sender;
    private String content;
    private MessageType type;
}
