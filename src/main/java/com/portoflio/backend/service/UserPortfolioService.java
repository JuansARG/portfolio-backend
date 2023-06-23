package com.portoflio.backend.service;

import com.portoflio.backend.dto.input.UserPortfolioDTO;
import com.portoflio.backend.exception.ArgumentInvalidException;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.exception.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserPortfolioService {

    List<UserPortfolio> getAllUsers() throws UserNotFoundException;
    UserPortfolio getUserById(Long id) throws UserNotFoundException;
    UserPortfolio createUser(UserPortfolioDTO user) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
    UserPortfolio updateUser(Long id, UserPortfolioDTO user) throws UserNotFoundException;
    UserPortfolio updateUserTitle(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolio updateUserProfile(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolio updateUserImage(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolio updateUserHardSkills(Long id, Set<String> value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolio updateUserSoftSkills(Long id, Set<String> value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolio addSoftSkills(Long id, List<String> value) throws UserNotFoundException, ArgumentInvalidException;
    UserPortfolio addHardSkills(Long id, List<String> value) throws UserNotFoundException, ArgumentInvalidException;
}
