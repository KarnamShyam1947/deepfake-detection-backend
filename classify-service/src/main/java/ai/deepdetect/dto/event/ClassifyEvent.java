package ai.deepdetect.dto.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassifyEvent {

    private int userId;
    private Date startDate;
    private String videoUrl;
    private String requestId;
    private String authToken;

}
