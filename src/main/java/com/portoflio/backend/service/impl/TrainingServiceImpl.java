package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.TrainingRequest;
import com.portoflio.backend.dto.response.TrainingResponse;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.TrainingNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.Training;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.repository.TrainingRepository;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.TrainingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final UserPortfolioRepository userPortfolioRepository;
    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(UserPortfolioRepository userPortfolioRepository, TrainingRepository trainingRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public UserPortfolioResponse addTraining(Long id, TrainingRequest training) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonthStartDate = YearMonth.parse(training.getStartDate(), formatter);

        Training newTraining = trainingRepository.save(Training.builder()
                .title(training.getTitle())
                .educationEntity(training.getEducationEntity())
                .inProgress(training.isInProgress())
                .startDate(yearMonthStartDate.atDay(1))
                .certificateURL(training.getCertificateURL())
                .userPortfolio(userDB)
                .build());

        if(!newTraining.isInProgress()){
            YearMonth yearMonthEndDate = YearMonth.parse(training.getEndDate(), formatter);
            newTraining.setEndDate(yearMonthEndDate.atDay(1));
            trainingRepository.save(newTraining);
        }

        return new UserPortfolioResponse(userDB);
    }

    @Override
    public TrainingResponse updateTraining(Long id, TrainingRequest training) throws UserNotFoundException, TrainingNotFoundException {

        Training trainingDB = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException("No existe una formación con ID: " + id));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonthStartDate = YearMonth.parse(training.getStartDate(), formatter);
        YearMonth yearMonthEndDate = YearMonth.parse(training.getEndDate(), formatter);


        trainingDB.setTitle(training.getTitle());
        trainingDB.setEducationEntity(training.getEducationEntity());
        trainingDB.setInProgress(trainingDB.isInProgress());
        trainingDB.setStartDate(yearMonthStartDate.atDay(1));
        trainingDB.setEndDate(yearMonthEndDate.atDay(1));
        trainingDB.setCertificateURL(training.getCertificateURL());

        return new TrainingResponse(trainingRepository.save(trainingDB));
    }
}
