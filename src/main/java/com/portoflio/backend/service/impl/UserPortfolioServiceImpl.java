package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.input.InUserPortfolioDTO;
import com.portoflio.backend.dto.output.OutUserPortfolioDTO;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.mapper.UserPortfolioMapper;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.UserPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {

    @Autowired
    UserPortfolioMapper mapper;
    @Autowired
    UserPortfolioRepository userPortfolioRepository;

    @Override
    public List<OutUserPortfolioDTO> getAllUsers() throws UserNotFoundException {
        List<UserPortfolio> users = userPortfolioRepository.findAll();
        if ( users.isEmpty() ) throw new UserNotFoundException("No hay usuarios registrados.");
        return users.stream().map((user) -> mapper.toOutDTO(user)).toList();
    }

    @Override
    public OutUserPortfolioDTO getUserById(Long id) throws UserNotFoundException {
        UserPortfolio user = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No hay existe un usuario con ID: " + id));
        return mapper.toOutDTO(user);
    }

    @Override
    public OutUserPortfolioDTO createUser(InUserPortfolioDTO user) throws UserNotFoundException {
        if( userPortfolioRepository.existsByEmail(user.getEmail()) ){
            throw new UserNotFoundException("Ya existe un usuario con ese email.");
        }
        UserPortfolio newUser = mapper.toUserPortfolio(user);
        return mapper.toOutDTO(userPortfolioRepository.save(newUser));
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if( !userPortfolioRepository.existsById(id) ) throw new UserNotFoundException("No existe un usuario con ID: " + id);
        userPortfolioRepository.deleteById(id);
    }

    @Override
    public OutUserPortfolioDTO updateUser(Long id, InUserPortfolioDTO user) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        userDB.setName(user.getName());
        userDB.setSurname(user.getSurname());
        userDB.setAge(user.getAge());
        userDB.setCity(user.getCity());
        userDB.setProfile(user.getProfile());
        userDB.setImageURL(user.getImageURL());
        userDB.setHardSkills(user.getHardSkills());
        userDB.setSoftSkills(user.getSoftSkills());

        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO updateUserTitle(Long id, String title) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( title.isEmpty() ) throw new ArgumentInvalidException("El título no puede estar vacío.");
        userDB.setTitle(title);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO updateUserProfile(Long id, String profile) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( profile.isEmpty() ) throw  new ArgumentInvalidException("La descripcion del perfil no puede estar vacío.");
        userDB.setProfile(profile);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO updateUserImage(Long id, String urlImage) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( urlImage.isEmpty() ) throw new ArgumentInvalidException("La url de la imagen no puede estar vacía.");
        userDB.setImageURL(urlImage);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO updateUserHardSkills(Long id, Set<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("La habilidades tecnicas no pueden estar vacias");
        userDB.setHardSkills(skills);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO updateUserSoftSkills(Long id, Set<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades blandas que desea agregar no pueden estar vacias");
        userDB.setSoftSkills(skills);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO addSoftSkills(Long id, List<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades blandas que desea agregar no pueden estar vacias");
        userDB.addSoftSkills(skills);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public OutUserPortfolioDTO addHardSkills(Long id, List<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades tecnicas que desea agregar no pueden estar vacias");
        userDB.addHardSkills(skills);
        return mapper.toOutDTO(userPortfolioRepository.save(userDB));
    }
}
