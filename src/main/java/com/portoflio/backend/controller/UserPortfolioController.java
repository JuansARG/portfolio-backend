package com.portoflio.backend.controller;

import com.portoflio.backend.dto.input.UserPortfolioDTO;
import com.portoflio.backend.exception.ArgumentInvalidException;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.exception.UserNotFoundException;
import com.portoflio.backend.service.UserPortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class UserPortfolioController {

    @Autowired
    UserPortfolioService userPortfolioService;

    @GetMapping("/users")
    public ResponseEntity<List<UserPortfolio>> getAllUsers() throws UserNotFoundException {
        List<UserPortfolio> users = userPortfolioService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserPortfolio> getUserById(@PathVariable Long id) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDB);
    }

    @PostMapping("/user/new")
    public ResponseEntity<UserPortfolio> createUser(@Valid @RequestBody UserPortfolioDTO user) throws UserNotFoundException {
        UserPortfolio newUser = userPortfolioService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userPortfolioService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserPortfolio> updateUser(@PathVariable Long id,@Valid @RequestBody UserPortfolioDTO user) throws UserNotFoundException {
        UserPortfolio updatedUser = userPortfolioService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/title")
    public ResponseEntity<UserPortfolio> updateUserTitle(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.updateUserTitle(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/profile")
    public ResponseEntity<UserPortfolio> updateUserProfile(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.updateUserProfile(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/image")
    public ResponseEntity<UserPortfolio> updateUserImage(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.updateUserImage(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/hard/skills")
    public ResponseEntity<UserPortfolio> updateUserHardSkills(@PathVariable Long id, @RequestParam Set<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.updateUserHardSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/soft/skills")
    public ResponseEntity<UserPortfolio> updateUserSoftSkills(@PathVariable Long id, @RequestParam Set<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.updateUserSoftSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/soft/skills/add")
    public ResponseEntity<UserPortfolio> addSoftSkills(@PathVariable Long id, @RequestParam List<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.addSoftSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/hard/skills/add")
    public ResponseEntity<UserPortfolio> addHardSkills(@PathVariable Long id, @RequestParam List<String> value) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio updatedUser = userPortfolioService.addHardSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
