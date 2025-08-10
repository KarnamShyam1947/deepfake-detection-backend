package ai.deepdetect.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.deepdetect.dto.request.ForgotPasswordRequest;
import ai.deepdetect.dto.request.LoginRequest;
import ai.deepdetect.dto.request.RegisterRequest;
import ai.deepdetect.dto.request.SetPasswordRequest;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.exceptions.OTPExpiredException;
import ai.deepdetect.exceptions.UserNotFoundException;
import ai.deepdetect.repositories.UserRepository;
import ai.deepdetect.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

     public UserEntity getUserById(int id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isEmpty())
            throw new UserNotFoundException();

        return userEntity.get();
    }

    public UserEntity getUserByEmail(String email) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)
            throw new UserNotFoundException(email);

        return userEntity;
    }
    
    public UserEntity getUserByToken(String token) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByToken(token);

        if(userEntity == null)
            throw new UserNotFoundException(token);

        return userEntity;
    }

    public UserEntity registerUser(RegisterRequest registerRequest) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(registerRequest.getEmail());

        if(userEntity != null)
            return null;

        UserEntity newUser = new UserEntity();
        BeanUtils.copyProperties(registerRequest, newUser);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole("USER");
        newUser.setActive(false);
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setExpirationDate(DateTimeUtils.addHours(1));

        UserEntity save = userRepository.save(newUser);

        // TODO: Send activeate mail - kafka

        return save;
    }

    public UserEntity loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return userRepository.findByEmail(loginRequest.getEmail());

    }
    
    public UserEntity forgotPassword(String email) throws UserNotFoundException {
        UserEntity userByEmail = getUserByEmail(email);

        userByEmail.setToken(UUID.randomUUID().toString());
        userByEmail.setExpirationDate(DateTimeUtils.addHours(1));
        UserEntity newUser = userRepository.save(userByEmail);
        
        // TODO: Send Mail

        return newUser;
    }

    public UserEntity setUserPassword(SetPasswordRequest setPasswordRequest) throws UserNotFoundException, OTPExpiredException {
        UserEntity userByToken = getUserByToken(setPasswordRequest.getToken());

        if (DateTimeUtils.isTokenInTime(userByToken.getExpirationDate())) {
            userByToken.setPassword(passwordEncoder.encode(setPasswordRequest.getPassword()));
            userByToken.setExpirationDate(null);
            userByToken.setToken(null);

            userRepository.save(userByToken);
        }

        return userByToken;
    }

    public UserEntity activateUser(String token) throws UserNotFoundException {
        UserEntity userByToken = getUserByToken(token);
        userByToken.setActive(true);
        userByToken.setToken(null);
        userByToken.setExpirationDate(null);
        return userRepository.save(userByToken);
    }
}
