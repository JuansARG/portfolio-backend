package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.input.UserPortfolioDTO;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.UserPortfolioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserPortfolioRepository userPortfolioRepository;

    @Override
    public List<UserPortfolio> getAllUsers() throws UserNotFoundException {
        List<UserPortfolio> users = userPortfolioRepository.findAll();
        if ( users.isEmpty() ) throw new UserNotFoundException("No hay usuarios registrados.");
        return users;
    }

    @Override
    public UserPortfolio getUserById(Long id) throws UserNotFoundException {
        UserPortfolio user = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No hay existe un usuario con ID: " + id));
        return user;
    }

    @Override
    public UserPortfolio createUser(UserPortfolioDTO user) throws UserNotFoundException {
        if( userPortfolioRepository.existByEmail(user.getEmail()) ){
            throw new UserNotFoundException("Ya existe un usuario con ese email.");
        }



//        UserPortfolio newUser = UserPortfolio
//                .builder()
//                .name(user.getName())
//                .surname(user.getSurname())
//                .age(user.getAge())
//                .city(user.getCity())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .title(user.getTitle())
//                .profile(user.getProfile())
//                .imageURL(user.getImageURL())
//                .hardSkills(user.getHardSkills())
//                .softSkills(user.getSoftSkills())
//                .build();

        UserPortfolio newUser = modelMapper.map(user, UserPortfolio.class);
        return userPortfolioRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if( !userPortfolioRepository.existsById(id) ) throw new UserNotFoundException("No existe un usuario con ID: " + id);
        userPortfolioRepository.deleteById(id);
    }

    @Override
    public UserPortfolio updateUser(Long id, UserPortfolioDTO user) throws UserNotFoundException {
        Optional<UserPortfolio> userDB = userPortfolioRepository.findById(id);
        if ( userDB.isEmpty() ) throw new UserNotFoundException("No existe un usuario con ID: " + id);
        userDB.get().setName(user.getName());
        userDB.get().setSurname(user.getSurname());
        userDB.get().setAge(user.getAge());
        userDB.get().setCity(user.getCity());
        userDB.get().setProfile(user.getProfile());
        userDB.get().setHardSkills(user.getHardSkills());
        userDB.get().setSoftSkills(user.getSoftSkills());
        return userPortfolioRepository.save(userDB.get());
    }

    @Override
    public UserPortfolio updateUserTitle(Long id, String title) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( title.isEmpty() ) throw new ArgumentInvalidException("El título no puede estar vacío.");
        userDB.setTitle(title);
        return userPortfolioRepository.save(userDB);
    }

    @Override
    public UserPortfolio updateUserProfile(Long id, String profile) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( profile.isEmpty() ) throw  new ArgumentInvalidException("La descripcion del perfil no puede estar vacío.");
        userDB.setProfile(profile);
        return userPortfolioRepository.save(userDB);
    }

    @Override
    public UserPortfolio updateUserImage(Long id, String urlImage) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( urlImage.isEmpty() ) throw new ArgumentInvalidException("La url de la imagen no puede estar vacía.");
        userDB.setImageURL(urlImage);
        return userPortfolioRepository.save(userDB);
    }

    @Override
    public UserPortfolio updateUserHardSkills(Long id, Set<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("La habilidades tecnicas no pueden estar vacias");
        userDB.setHardSkills(skills);
        return userPortfolioRepository.save(userDB);
    }

    @Override
    public UserPortfolio updateUserSoftSkills(Long id, Set<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades blandas que desea agregar no pueden estar vacias");
        userDB.setSoftSkills(skills);
        return userPortfolioRepository.save(userDB);
    }

    @Override
    public UserPortfolio addSoftSkills(Long id, List<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades blandas que desea agregar no pueden estar vacias");
        userDB.addSoftSkills(skills);
        return userPortfolioRepository.save(userDB);
    }

    @Override
    public UserPortfolio addHardSkills(Long id, List<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades tecnicas que desea agregar no pueden estar vacias");
        userDB.addHardSkills(skills);
        return userPortfolioRepository.save(userDB);
    }
}
