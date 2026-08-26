package org.princeworks.chessora.response.user;

import lombok.Data;

@Data
public class SignInResponse {
    private String token;
    private String userName;

    public SignInResponse(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }
}
