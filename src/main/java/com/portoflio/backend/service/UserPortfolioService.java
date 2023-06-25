package com.portoflio.backend.service;

import com.portoflio.backend.dto.input.InUserPortfolioDTO;
import com.portoflio.backend.dto.output.OutUserPortfolioDTO;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserPortfolioService {

    List<OutUserPortfolioDTO> getAllUsers() throws UserNotFoundException;
    OutUserPortfolioDTO getUserById(Long id) throws UserNotFoundException;
    OutUserPortfolioDTO createUser(InUserPortfolioDTO user) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;

    OutUserPortfolioDTO updateUser(Long id, InUserPortfolioDTO user) throws UserNotFoundException;

    OutUserPortfolioDTO updateUserTitle(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    OutUserPortfolioDTO updateUserProfile(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    OutUserPortfolioDTO updateUserImage(Long id, String value) throws UserNotFoundException, ArgumentInvalidException;
    OutUserPortfolioDTO updateUserHardSkills(Long id, Set<String> value) throws UserNotFoundException, ArgumentInvalidException;
    OutUserPortfolioDTO updateUserSoftSkills(Long id, Set<String> value) throws UserNotFoundException, ArgumentInvalidException;
    OutUserPortfolioDTO addSoftSkills(Long id, List<String> value) throws UserNotFoundException, ArgumentInvalidException;
    OutUserPortfolioDTO addHardSkills(Long id, List<String> value) throws UserNotFoundException, ArgumentInvalidException;
}
