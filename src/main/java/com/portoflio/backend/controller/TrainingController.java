package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.TrainingRequest;
import com.portoflio.backend.dto.response.TrainingResponse;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.TrainingNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/training")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PatchMapping("/add-to/{id}")
    public ResponseEntity<UserPortfolioResponse> addTraining(@PathVariable Long id, @Valid @RequestBody TrainingRequest training) throws UserNotFoundException {
        UserPortfolioResponse updateUser = trainingService.addTraining(id, training);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrainingResponse> updateTraining(@PathVariable Long id, @Valid @RequestBody TrainingRequest training) throws UserNotFoundException, TrainingNotFoundException {
        TrainingResponse updateTraining = trainingService.updateTraining(id, training);
        return ResponseEntity.status(HttpStatus.OK).body(updateTraining);

    }
}
