package org.princeworks.chessora.response.user;

import lombok.Data;

@Data
public class SignInResponse {
    private String userName;
    private String email;

    public SignInResponse(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
