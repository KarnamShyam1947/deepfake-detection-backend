package ai.deepdetect.dto.response;

import org.springframework.beans.BeanUtils;

import ai.deepdetect.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String role;
    private String email;
    private String phone;

    public static UserResponse entityToResponse(UserEntity entity) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(entity, userResponse);

        return userResponse;
    }
    
    public static UserEntity responseToEntity(UserResponse userResponse) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userResponse, userEntity);

        return userEntity;
    }
}
