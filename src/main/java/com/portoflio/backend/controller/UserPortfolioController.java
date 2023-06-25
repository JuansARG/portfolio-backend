package com.portoflio.backend.controller;

import com.portoflio.backend.dto.input.InUserPortfolioDTO;
import com.portoflio.backend.dto.output.OutUserPortfolioDTO;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.exception.model.UserNotFoundException;
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
    public ResponseEntity<List<OutUserPortfolioDTO>> getAllUsers() throws UserNotFoundException {
        List<OutUserPortfolioDTO> users = userPortfolioService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<OutUserPortfolioDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        OutUserPortfolioDTO userDB = userPortfolioService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDB);
    }

    @PostMapping("/user/new")
    public ResponseEntity<OutUserPortfolioDTO> createUser(@Valid @RequestBody InUserPortfolioDTO user) throws UserNotFoundException {
        OutUserPortfolioDTO newUser = userPortfolioService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userPortfolioService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<OutUserPortfolioDTO> updateUser(@PathVariable Long id, @RequestBody InUserPortfolioDTO user) throws UserNotFoundException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/title")
    public ResponseEntity<OutUserPortfolioDTO> updateUserTitle(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.updateUserTitle(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/profile")
    public ResponseEntity<OutUserPortfolioDTO> updateUserProfile(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.updateUserProfile(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/image")
    public ResponseEntity<OutUserPortfolioDTO> updateUserImage(@PathVariable Long id, @RequestParam String value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.updateUserImage(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/hard/skills")
    public ResponseEntity<OutUserPortfolioDTO> updateUserHardSkills(@PathVariable Long id, @RequestParam Set<String> value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.updateUserHardSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/soft/skills")
    public ResponseEntity<OutUserPortfolioDTO> updateUserSoftSkills(@PathVariable Long id, @RequestParam Set<String> value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.updateUserSoftSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/soft/skills/add")
    public ResponseEntity<OutUserPortfolioDTO> addSoftSkills(@PathVariable Long id, @RequestParam List<String> value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.addSoftSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/user/{id}/hard/skills/add")
    public ResponseEntity<OutUserPortfolioDTO> addHardSkills(@PathVariable Long id, @RequestParam List<String> value) throws UserNotFoundException, ArgumentInvalidException {
        OutUserPortfolioDTO updatedUser = userPortfolioService.addHardSkills(id, value);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
