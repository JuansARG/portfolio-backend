package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.ChangePasswordRequest;
import com.portoflio.backend.dto.request.ResetPasswordRequest;
import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.AuthService;
import com.portoflio.backend.service.UserPortfolioService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserPortfolioService userPortfolioService;
    private final AuthService authService;

    public AuthController(UserPortfolioService userPortfolioService, AuthService authService) {
        this.userPortfolioService = userPortfolioService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserPortfolioResponse> createUser(@Valid @RequestBody UserPortfolioRequest user) throws UserNotFoundException {
        UserPortfolioResponse newUser = userPortfolioService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/{id}/verify-account")
    public ResponseEntity<?> verifyAccount(@PathVariable Long id) throws UserNotFoundException {
        authService.verifyAccount(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest values) throws UserNotFoundException {
        authService.changePassword(id, values);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/password-reset-request")
    public ResponseEntity<?> passwordResetRequest(@RequestParam String email) throws UserNotFoundException, MessagingException {
        if(authService.passwordResetRequest(email)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> passwordReset(@RequestParam String email, @RequestParam String code, @Valid @RequestBody ResetPasswordRequest value) throws UserNotFoundException {
        if(authService.passwordReset(email, code, value)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
