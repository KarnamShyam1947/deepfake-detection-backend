package ai.deepdetect.config.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import ai.deepdetect.config.security.oauth.users.BaseOAuthUser;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        BaseOAuthUser oAuth2UserInfo = CustomOAuthUserFactory.getOAuth2UserInfo(
            userRequest.getClientRegistration().getRegistrationId(), 
            user.getAttributes()
        );

        UserEntity userEntity = oAuthUserToEntity(oAuth2UserInfo);

        return new CustomOAuthUser(userEntity, user.getAttributes());
    }

    private UserEntity oAuthUserToEntity(BaseOAuthUser oAuthUser) {

        UserEntity existingUser = repository.findByAuthProviderId(oAuthUser.getId());
        if (existingUser != null) 
            return existingUser;
        
        
        UserEntity user = new UserEntity();

        user.setRole("USER");
        user.setName(oAuthUser.getName());
        user.setEmail(oAuthUser.getEmail());
        user.setAuthProviderId(oAuthUser.getId());
        user.setAuthProvider(oAuthUser.getAuthProvider());

        return repository.save(user);
    }
    
}
