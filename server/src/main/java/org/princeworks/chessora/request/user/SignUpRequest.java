package org.princeworks.chessora.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
    @Email
    @NotBlank
    @Size (max = 50)
    private String email;
    
    @NotBlank
    @Size (max = 50)
    private String username;
    
    @NotBlank
    @Size (min = 3, max = 50)
    private String firstName;
    
    @Size (max = 50)
    private String lastName;
    
    @NotBlank
    @Size (min = 8, max = 50)
    private String password;
}
