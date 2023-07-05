package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.ChangePasswordRequest;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.AuthService;
import com.portoflio.backend.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserPortfolioRepository userPortfolioRepository;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void verifyAccount(Long id) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        if(userDB.isVerified()) return;
        userDB.setVerified(true);
        userPortfolioRepository.save(userDB);
        emailUtils.sendConfirmationMessage(userDB.getEmail());
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequest userUpdate) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        // tener cuidado con el orden en que paso los params!
        if(!passwordEncoder.matches(userUpdate.getPassword(), userDB.getPassword())){
            throw new UserNotFoundException("Credenciales invalidas.");
        }

        userDB.setPassword(passwordEncoder.encode(userUpdate.getNewPassword()));
        userPortfolioRepository.save(userDB);
        emailUtils.sendPasswordChangeNotification(userDB.getEmail());
    }
}
