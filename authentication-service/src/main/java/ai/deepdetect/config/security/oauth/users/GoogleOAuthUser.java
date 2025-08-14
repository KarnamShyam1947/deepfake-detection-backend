package ai.deepdetect.config.security.oauth.users;

import java.util.Map;

public class GoogleOAuthUser extends BaseOAuthUser {

    public GoogleOAuthUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (attributes.get("sub")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        String email = (String) attributes.get("email");
        
        return email;
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getAuthProvider() {
        return "GOOGLE";
    }
    
}
