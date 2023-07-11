package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.TrainingRequest;
import com.portoflio.backend.dto.response.TrainingResponse;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.TrainingNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface TrainingService {

    UserPortfolioResponse addTraining(Long id, TrainingRequest training) throws UserNotFoundException;

    TrainingResponse updateTraining(Long id, TrainingRequest training) throws UserNotFoundException, TrainingNotFoundException;
}
