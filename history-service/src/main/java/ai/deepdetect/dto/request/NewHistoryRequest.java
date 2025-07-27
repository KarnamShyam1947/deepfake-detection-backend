package ai.deepdetect.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewHistoryRequest {
    private String videoUrl;
    private String requestId;
    private Date startDate;
    private int userId;
}
