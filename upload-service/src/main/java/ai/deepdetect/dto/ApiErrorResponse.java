package ai.deepdetect.dto;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

    private String path;
    private String error;
    private String status;
    private int statusCode;
    private Date timestamp;
    private String errorReason;

    private Map<String, Object> formErrors;

    // TODO: fix to IST 
    public ApiErrorResponse() {
        this.timestamp = new Date();
    }

}
