package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.TrainingRequest;
import com.portoflio.backend.dto.response.TrainingResponse;
import com.portoflio.backend.exception.model.TrainingNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.Training;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.repository.TrainingRepository;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final UserPortfolioRepository userPortfolioRepository;
    private final TrainingRepository trainingRepository;

    @Override
    public TrainingResponse addTraining(Long id, TrainingRequest training) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

//        esto sería la implementación si en el body recibiera una fecha por ejemplo: "08/2022"
        DateTimeFormatter formatterOLD = DateTimeFormatter.ofPattern("MM/yyyy");
//        YearMonth yearMonthStartDate = YearMonth.parse(training.getStartDate(), formatterOLD);
        DateTimeFormatter formatterNEW = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        Training newTraining = Training.builder()
                        .title(training.getTitle())
                        .educationEntity(training.getEducationEntity())
                        .inProgress(training.isInProgress())
                        .startDate(LocalDate.parse(training.getStartDate(), formatterNEW))
                        .certificateURL(training.getCertificateURL())
                        .userPortfolio(userDB)
                        .build();

        if(!newTraining.isInProgress()){
//            YearMonth yearMonthEndDate = YearMonth.parse(training.getEndDate(), formatterOLD);
            newTraining.setEndDate(LocalDate.parse(training.getEndDate(), formatterNEW));
        }

        return new TrainingResponse(trainingRepository.save(newTraining));
    }

    @Override
    public TrainingResponse updateTraining(Long id, TrainingRequest training) throws UserNotFoundException, TrainingNotFoundException {
        Training trainingDB = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException("No existe una formación con ID: " + id));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        trainingDB.setTitle(training.getTitle());
        trainingDB.setEducationEntity(training.getEducationEntity());
        trainingDB.setInProgress(trainingDB.isInProgress());
        trainingDB.setStartDate(LocalDate.parse(training.getStartDate(), formatter));
        trainingDB.setEndDate(LocalDate.parse(training.getEndDate(), formatter));
        trainingDB.setCertificateURL(training.getCertificateURL());

        return new TrainingResponse(trainingRepository.save(trainingDB));
    }

    @Override
    public void deleteTraining(Long idTraining, Long idUser) throws TrainingNotFoundException, UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + idUser));

        if( !trainingRepository.existsById(idTraining) ) throw new TrainingNotFoundException("No existe una formación con ID: " + idTraining);

        userDB.setFormations(
                userDB.getFormations()
                        .stream()
                        .filter( training -> !training.getId().equals(idTraining) )
                        .collect(Collectors.toSet())
        );

        userPortfolioRepository.save(userDB);
        trainingRepository.deleteById(idTraining);
    }
}
