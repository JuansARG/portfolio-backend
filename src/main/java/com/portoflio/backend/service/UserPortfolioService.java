package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.request.UserPortfolioUpdateRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;

import java.util.List;

public interface UserPortfolioService {

    List<UserPortfolioResponse> getAllUsers() throws UserNotFoundException;
    UserPortfolioResponse getUserById(Long id) throws UserNotFoundException;
    UserPortfolioResponse createUser(UserPortfolioRequest userRequest) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
    UserPortfolioResponse updateUser(Long id, UserPortfolioUpdateRequest userRequest) throws UserNotFoundException;
    UserPortfolioResponse updateUserTitle(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse updateUserProfile(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse updateUserImage(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
}
