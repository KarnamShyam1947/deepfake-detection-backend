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

    private String authToken;
    private String requestId;
    private String filename;
    private String videoUrl;
    private Date startDate;
    private int userId;
    private double size;

}
