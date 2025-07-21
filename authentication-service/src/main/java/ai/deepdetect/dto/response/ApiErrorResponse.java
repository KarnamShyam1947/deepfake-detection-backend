package ai.deepdetect.dto.response;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private String path;
    private String error;
    private String status;
    private int statusCode;
    private Date timestamp;
    private String errorReason;

    private Map<String, Object> formErrors;

}
