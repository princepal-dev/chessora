package org.princeworks.chessora.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    @NotBlank
    private String password;
}
