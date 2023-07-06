package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.ChangePasswordRequest;
import com.portoflio.backend.dto.request.ResetPasswordRequest;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.AuthService;
import com.portoflio.backend.utils.EmailUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public void changePassword(Long id, ChangePasswordRequest values) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        // tener cuidado con el orden en que paso los params!
        if(!passwordEncoder.matches(values.getPassword(), userDB.getPassword())){
            throw new UserNotFoundException("Credenciales invalidas.");
        }

        userDB.setPassword(passwordEncoder.encode(values.getNewPassword()));
        userPortfolioRepository.save(userDB);
        emailUtils.sendPasswordChangeNotification(userDB.getEmail());
    }

    @Override
    public boolean passwordResetRequest(String email) throws UserNotFoundException {
        Optional<UserPortfolio> userDB = userPortfolioRepository.findByEmail(email);

        if( userDB.isEmpty() ) return false;

        userDB.get().setPasswordResetCode(UUID.randomUUID().toString());
        userPortfolioRepository.save(userDB.get());

        emailUtils.sendLinkToResetPassword(email, userDB.get().getPasswordResetCode());
        return true;
    }

    @Override
    public boolean passwordReset(String email, String code, ResetPasswordRequest value) throws UserNotFoundException {
        Optional<UserPortfolio> userDB = userPortfolioRepository.findByEmail(email);

        if(userDB.isEmpty()) return false;
        if( !userDB.get().getPasswordResetCode().equals(code) ) return false;

        userDB.get().setPassword(passwordEncoder.encode(value.getNewPassword()));
        userDB.get().setPasswordResetCode(null);
        userPortfolioRepository.save(userDB.get());
        emailUtils.sendPasswordChangeNotification(userDB.get().getEmail());
        return true;
    }
}
