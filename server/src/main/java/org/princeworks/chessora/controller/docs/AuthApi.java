package org.princeworks.chessora.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.princeworks.chessora.common.CommonResponse;
import org.princeworks.chessora.request.user.ForgotPasswordRequest;
import org.princeworks.chessora.request.user.PasswordResetRequest;
import org.princeworks.chessora.request.user.SignInRequest;
import org.princeworks.chessora.request.user.SignUpRequest;
import org.princeworks.chessora.response.user.SignInResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {

    @Operation(
            summary = "Sign in to Chessora",
            description =
                    "Authenticates a user using username and password and sets a JWT in an HTTP-only cookie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully signed in"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password"),
            @ApiResponse(responseCode = "403", description = "Email address has not been verified")
    })
    ResponseEntity<CommonResponse<SignInResponse>> authenticateUser(
            @RequestBody SignInRequest signInRequest);


    @Operation(
            summary = "Register a new user",
            description =
                    "Creates a new Chessora account and sends an email verification link to the registered email address.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Email or username is already registered")
    })
    ResponseEntity<CommonResponse<Void>> registerUser(
            @RequestBody SignUpRequest signUpRequest);


    @Operation(
            summary = "Verify email address",
            description =
                    "Verifies a user's email address using the verification token sent during registration.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid, expired, used, or incorrect token")
    })
    ResponseEntity<CommonResponse<Void>> verifyEmail(
            @PathVariable String token);


    @Operation(
            summary = "Request password reset",
            description =
                    "Sends a password reset token to the user's verified email address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset token sent successfully"),
            @ApiResponse(responseCode = "400", description = "Email is not registered or not verified")
    })
    ResponseEntity<CommonResponse<Void>> forgotPassword(
            @RequestBody ForgotPasswordRequest passwordResetRequest);


    @Operation(
            summary = "Reset password",
            description =
                    "Resets the user's password using a valid password reset token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid, expired, used, or incorrect token")
    })
    ResponseEntity<CommonResponse<Void>> resetPassword(
            @PathVariable String token,
            @RequestBody PasswordResetRequest passwordResetRequest);


    @Operation(
            summary = "Log out",
            description =
                    "Logs out the current user by clearing the authentication JWT cookie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged out")
    })
    ResponseEntity<CommonResponse<Void>> logout();
}