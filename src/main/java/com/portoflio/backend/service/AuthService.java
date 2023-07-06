package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.ChangePasswordRequest;
import com.portoflio.backend.dto.request.ResetPasswordRequest;
import com.portoflio.backend.exception.model.UserNotFoundException;
import jakarta.mail.MessagingException;

public interface AuthService {

    void verifyAccount(Long id) throws UserNotFoundException;
    void changePassword(Long id, ChangePasswordRequest userUpdate) throws UserNotFoundException;
    boolean passwordResetRequest(String email) throws UserNotFoundException, MessagingException;
    boolean passwordReset(String email, String code, ResetPasswordRequest value) throws UserNotFoundException;
}
