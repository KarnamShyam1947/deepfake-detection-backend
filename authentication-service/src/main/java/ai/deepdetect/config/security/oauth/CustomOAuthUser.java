package ai.deepdetect.config.security.oauth;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.deepdetect.entities.UserEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuthUser implements OAuth2User {

    private final UserEntity userEntity;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userEntity.getRole()));
    }

    @Override
    public String getName() {
        return userEntity.getName();
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
    
}
