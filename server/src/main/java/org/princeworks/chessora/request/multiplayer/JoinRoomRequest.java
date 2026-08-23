package org.princeworks.chessora.request.multiplayer;

import lombok.Getter;
import lombok.Setter;
import org.princeworks.chessora.entity.game.PlayerColor;
import org.princeworks.chessora.entity.multiplayer.Role;

@Getter
@Setter
public class JoinRoomRequest {
    private Role role;
    private Boolean isGuest;
    private PlayerColor playerColor;
}
