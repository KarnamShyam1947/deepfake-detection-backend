package ai.deepdetect.config.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ai.deepdetect.clients.AuthenticationClient;
import ai.deepdetect.dto.UserResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AuthenticationClient authenticationClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse user = authenticationClient.getUserEntityByEmail(username).getBody();

        if(user != null)
            return new CustomUserDetails(user);

        throw new UsernameNotFoundException("invalid user credentials");
    }
    
}
