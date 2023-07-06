package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.mapper.UserPortfolioMapper;
import com.portoflio.backend.model.ERole;
import com.portoflio.backend.model.Role;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.UserPortfolioService;
import com.portoflio.backend.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserPortfolioMapper mapper;
    @Autowired
    UserPortfolioRepository userPortfolioRepository;
    @Autowired
    EmailUtils emailUtils;

    @Override
    public List<UserPortfolioResponse> getAllUsers() throws UserNotFoundException {
        List<UserPortfolio> users = userPortfolioRepository.findAll();
        if ( users.isEmpty() ) throw new UserNotFoundException("No hay usuarios registrados.");
        return users.stream()
                .map((user) -> mapper.toDTO(user))
                .toList();
    }

    @Override
    public UserPortfolioResponse getUserById(Long id) throws UserNotFoundException {
        UserPortfolio user = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No hay existe un usuario con ID: " + id));
        return mapper.toDTO(user);
    }

    @Override
    public UserPortfolioResponse createUser(UserPortfolioRequest userRequest) throws UserNotFoundException {
        if( userPortfolioRepository.existsByEmail(userRequest.getEmail()) ){
            throw new UserNotFoundException("Ya existe un usuario con ese email.");
        }
        UserPortfolio newUser = mapper.toUserPortfolio(userRequest);
        newUser.setVerified(false);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Role roles = Role.builder().build();

        if (newUser.getEmail().equals("juansarmientog96@gmail.com")){
            roles.setName(ERole.ADMIN);
            newUser.setRoles(Set.of(roles));
        }else {
            roles.setName(ERole.INVITED);
            newUser.setRoles(Set.of(roles));
        }
        UserPortfolio userSaved = userPortfolioRepository.save(newUser);
        emailUtils.sendMessageToVerify(newUser.getEmail(), userSaved.getId());
        return mapper.toDTO(userSaved);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if( !userPortfolioRepository.existsById(id) ) throw new UserNotFoundException("No existe un usuario con ID: " + id);
        userPortfolioRepository.deleteById(id);
    }

    @Override
    public UserPortfolioResponse updateUser(Long id, UserPortfolioRequest userRequest) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        userDB.setName(userRequest.getName());
        userDB.setSurname(userRequest.getSurname());
        userDB.setAge(userRequest.getAge());
        userDB.setCity(userRequest.getCity());
        userDB.setTitle(userRequest.getTitle());
        userDB.setProfile(userRequest.getProfile());
        userDB.setImageURL(userRequest.getImageURL());
        userDB.setHardSkills(userRequest.getHardSkills());
        userDB.setSoftSkills(userRequest.getSoftSkills());

        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserTitle(Long id, String title) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( title.isEmpty() ) throw new ArgumentInvalidException("El título no puede estar vacío.");
        userDB.setTitle(title);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserProfile(Long id, String profile) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( profile.isEmpty() ) throw new ArgumentInvalidException("La descripción del perfil no puede estar vacío.");
        userDB.setProfile(profile);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserImage(Long id, String urlImage) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( urlImage.isEmpty() ) throw new ArgumentInvalidException("La url de la imagen no puede estar vacía.");
        userDB.setImageURL(urlImage);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserHardSkills(Long id, Set<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("La habilidades técnicas no pueden estar vacías");
        userDB.setHardSkills(skills);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserSoftSkills(Long id, Set<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades blandas que desea agregar no pueden estar vacías");
        userDB.setSoftSkills(skills);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse addUserSoftSkills(Long id, List<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades blandas que desea agregar no pueden estar vacías");
        userDB.addSoftSkills(skills);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse addUserHardSkills(Long id, List<String> skills) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( skills.size() == 0 ) throw new ArgumentInvalidException("Las habilidades técnicas que desea agregar no pueden estar vacías");
        userDB.addHardSkills(skills);
        return mapper.toDTO(userPortfolioRepository.save(userDB));
    }
}
