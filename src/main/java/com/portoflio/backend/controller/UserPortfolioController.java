package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.UserPortfolioUpdateRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.UserPortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserPortfolioController {

    private final UserPortfolioService userPortfolioService;

    @GetMapping("/all")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<UserPortfolioResponse>> getAllUsers() throws UserNotFoundException {
        List<UserPortfolioResponse> users = userPortfolioService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<UserPortfolioResponse> getUserById(@PathVariable Long id) throws UserNotFoundException {
        UserPortfolioResponse userDB = userPortfolioService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDB);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userPortfolioService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserPortfolioResponse> updateUser(@PathVariable Long id, @RequestBody UserPortfolioUpdateRequest user) throws UserNotFoundException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/{id}/update/title")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserPortfolioResponse> updateUserTitle(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserTitle(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/{id}/update/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserPortfolioResponse> updateUserProfile(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserProfile(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/{id}/update/image")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserPortfolioResponse> updateUserImage(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserImage(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
