package ai.deepdetect.config.security.oauth.users;

import java.util.Map;

public class GitHubOAuthUser extends BaseOAuthUser {

    public GitHubOAuthUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("login");
    }

    @Override
    public String getEmail() {
        String email = (String) attributes.get("email");
        if (email == null) {
            return (attributes.get("id")).toString();
        }
        
        return email;
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }

    @Override
    public String getAuthProvider() {
        return "GITHUB";
    }
    
}
