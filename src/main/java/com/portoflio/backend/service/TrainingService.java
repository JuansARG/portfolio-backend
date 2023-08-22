package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.TrainingRequest;
import com.portoflio.backend.dto.response.TrainingResponse;
import com.portoflio.backend.exception.model.TrainingNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface TrainingService {

    TrainingResponse addTraining(Long id, TrainingRequest training) throws UserNotFoundException;

    TrainingResponse updateTraining(Long id, TrainingRequest training) throws UserNotFoundException, TrainingNotFoundException;

    void deleteTraining(Long idTraining, Long idUser) throws TrainingNotFoundException, UserNotFoundException;
}
