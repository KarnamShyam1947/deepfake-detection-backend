package ai.deepdetect.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHistoryRequest {

    private int userId;
    private Date endDate;
    private String result;
    private String status;
    private Date startDate;
    private String videoUrl;
    private String requestId;
    private double confidence;
    private String outputVideoUrl;
}
