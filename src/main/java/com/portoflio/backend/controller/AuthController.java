package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.ChangePasswordRequest;
import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.AuthService;
import com.portoflio.backend.service.UserPortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserPortfolioService userPortfolioService;

    @Autowired
    AuthService authService;

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
    public ResponseEntity<?> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest userUpdate) throws UserNotFoundException {
        authService.changePassword(id, userUpdate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
