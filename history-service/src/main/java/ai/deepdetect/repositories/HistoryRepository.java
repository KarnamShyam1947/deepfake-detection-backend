package ai.deepdetect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.deepdetect.entities.HistoryEntity;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    
    List<HistoryEntity> findByUserId(int id);
    HistoryEntity findById(int id);
    HistoryEntity findByRequestId(String requestId);
    
}
