package ai.deepdetect.config.security.oauth;

import java.util.Map;

import ai.deepdetect.config.security.oauth.users.BaseOAuthUser;
import ai.deepdetect.config.security.oauth.users.GitHubOAuthUser;
import ai.deepdetect.config.security.oauth.users.GoogleOAuthUser;

public class CustomOAuthUserFactory {

    public static BaseOAuthUser getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase("GOOGLE")) 
            return new GoogleOAuthUser(attributes);
            
        else if (registrationId.equalsIgnoreCase("GITHUB")) 
            return new GitHubOAuthUser(attributes);
        
        else 
            throw new RuntimeException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
}
