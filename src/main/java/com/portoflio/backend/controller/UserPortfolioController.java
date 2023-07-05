package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.UserPortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class UserPortfolioController {

    @Autowired
    UserPortfolioService userPortfolioService;

    @GetMapping("/users")
    public ResponseEntity<List<UserPortfolioResponse>> getAllUsers() throws UserNotFoundException {
        List<UserPortfolioResponse> users = userPortfolioService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserPortfolioResponse> getUserById(@PathVariable Long id) throws UserNotFoundException {
        UserPortfolioResponse userDB = userPortfolioService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDB);
    }

//    @PostMapping("/auth/signup")
//    public ResponseEntity<UserPortfolioResponse> createUser(@Valid @RequestBody UserPortfolioRequest user) throws UserNotFoundException {
//        UserPortfolioResponse newUser = userPortfolioService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userPortfolioService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserPortfolioResponse> updateUser(@PathVariable Long id, @RequestBody UserPortfolioRequest user) throws UserNotFoundException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/title")
    public ResponseEntity<UserPortfolioResponse> updateUserTitle(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserTitle(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/profile")
    public ResponseEntity<UserPortfolioResponse> updateUserProfile(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserProfile(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/image")
    public ResponseEntity<UserPortfolioResponse> updateUserImage(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserImage(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/hard/skills")
    public ResponseEntity<UserPortfolioResponse> updateUserHardSkills(@PathVariable Long id, @RequestParam Set<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserHardSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/soft/skills")
    public ResponseEntity<UserPortfolioResponse> updateUserSoftSkills(@PathVariable Long id, @RequestParam Set<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.updateUserSoftSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/soft/skills/add")
    public ResponseEntity<UserPortfolioResponse> addSoftSkills(@PathVariable Long id, @RequestParam List<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.addUserSoftSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/hard/skills/add")
    public ResponseEntity<UserPortfolioResponse> addHardSkills(@PathVariable Long id, @RequestParam List<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = userPortfolioService.addUserHardSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
