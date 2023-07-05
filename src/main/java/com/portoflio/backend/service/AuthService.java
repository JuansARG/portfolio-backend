package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.ChangePasswordRequest;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface AuthService {

    void verifyAccount(Long id) throws UserNotFoundException;

    void changePassword(Long id, ChangePasswordRequest userUpdate) throws UserNotFoundException;
}
