package ai.deepdetect.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history_details")
public class HistoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double size;
    private int userId;
    private Date endDate;
    private String result;
    private String status;
    private Date startDate;
    private String filename;
    private double duration;
    private String videoUrl;
    private String requestId;
    private double confidence;
    private String outputVideoUrl;
}
