package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.request.UserPortfolioUpdateRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.Role;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.model.enums.ERole;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.UserPortfolioService;
import com.portoflio.backend.utils.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserPortfolioServiceImpl implements UserPortfolioService {

    private final PasswordEncoder passwordEncoder;
    private final UserPortfolioRepository userPortfolioRepository;
    private final EmailUtils emailUtils;

    @Override
    public List<UserPortfolioResponse> getAllUsers() throws UserNotFoundException {
        List<UserPortfolio> users = userPortfolioRepository.findAll();
        if ( users.isEmpty() ) throw new UserNotFoundException("No hay usuarios registrados.");
        return users
                .stream()
                .map(UserPortfolioResponse::new)
                .toList();
    }

    @Override
    public UserPortfolioResponse getUserById(Long id) throws UserNotFoundException {
        UserPortfolio user = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        return new UserPortfolioResponse(user);
    }

    @Override
    public UserPortfolioResponse createUser(UserPortfolioRequest userRequest) throws UserNotFoundException {
        if( userPortfolioRepository.existsByEmail(userRequest.getEmail()) ){
            throw new UserNotFoundException("Ya existe un usuario con ese email.");
        }
        UserPortfolio newUser = new UserPortfolio(userRequest);
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
        return new UserPortfolioResponse(userSaved);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if( !userPortfolioRepository.existsById(id) ) throw new UserNotFoundException("No existe un usuario con ID: " + id);
        userPortfolioRepository.deleteById(id);
    }

    @Override
    public UserPortfolioResponse updateUser(Long id, UserPortfolioUpdateRequest userRequest) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        userDB.setName(userRequest.getName());
        userDB.setSurname(userRequest.getSurname());
        userDB.setAge(userRequest.getAge());
        userDB.setCity(userRequest.getCity());
        userDB.setTitle(userRequest.getTitle());
        userDB.setProfile(userRequest.getProfile());
        userDB.setImageURL(userRequest.getImageURL());

        return new UserPortfolioResponse(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserTitle(Long id, String title) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( title.isEmpty() ) throw new ArgumentInvalidException("El título no puede estar vacío.");
        userDB.setTitle(title);
        return new UserPortfolioResponse(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserProfile(Long id, String profile) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( profile.isEmpty() ) throw new ArgumentInvalidException("La descripción del perfil no puede estar vacío.");
        userDB.setProfile(profile);
        return new UserPortfolioResponse(userPortfolioRepository.save(userDB));
    }

    @Override
    public UserPortfolioResponse updateUserImage(Long id, String urlImage) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));
        if ( urlImage.isEmpty() ) throw new ArgumentInvalidException("La url de la imagen no puede estar vacía.");
        userDB.setImageURL(urlImage);
        return new UserPortfolioResponse(userPortfolioRepository.save(userDB));
    }
}
