package ai.deepdetect.services;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.deepdetect.dto.request.LoginRequest;
import ai.deepdetect.dto.request.RegisterRequest;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.exceptions.UserNotFoundException;
import ai.deepdetect.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserEntity getUserByEmail(String email) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity != null)
            throw new UserNotFoundException(email);

        return userEntity;
    }

    public UserEntity registerUser(RegisterRequest registerRequest) throws UserNotFoundException {
        UserEntity userEntity = getUserByEmail(registerRequest.getEmail());

        if(userEntity != null)
            return null;

        UserEntity newUser = new UserEntity();
        BeanUtils.copyProperties(registerRequest, newUser);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole("USER");

        return userRepository.save(newUser);
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
}
