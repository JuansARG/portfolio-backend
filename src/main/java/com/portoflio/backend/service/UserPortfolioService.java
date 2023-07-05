package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserPortfolioService {

    List<UserPortfolioResponse> getAllUsers() throws UserNotFoundException;
    UserPortfolioResponse getUserById(Long id) throws UserNotFoundException;
    UserPortfolioResponse createUser(UserPortfolioRequest userRequest) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
    UserPortfolioResponse updateUser(Long id, UserPortfolioRequest userRequest) throws UserNotFoundException;
    UserPortfolioResponse updateUserTitle(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse updateUserProfile(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse updateUserImage(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse updateUserHardSkills(Long id, Set<String> value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse updateUserSoftSkills(Long id, Set<String> value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse addUserSoftSkills(Long id, List<String> value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolioResponse addUserHardSkills(Long id, List<String> value) throws UserNotFoundException, ArgumentInvalidException;
}
