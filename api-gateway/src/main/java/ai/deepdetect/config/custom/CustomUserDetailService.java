package ai.deepdetect.config.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ai.deepdetect.dto.UserResponse;
import ai.deepdetect.services.AuthService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse user = authService.getUser(username);

        if(user != null)
            return new CustomUserDetails(user);

        throw new UsernameNotFoundException("invalid user credentials");
    }
    
}
