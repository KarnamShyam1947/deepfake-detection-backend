package ai.deepdetect.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAPIResponse {
    private int userId;
    private Date startDate;
    private String videoUrl;
    private String requestId;

    private String result;
    private double confidence;
    private String outputVideoUrl;
}
