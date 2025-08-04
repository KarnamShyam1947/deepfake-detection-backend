package ai.deepdetect.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import ai.deepdetect.dto.request.NewHistoryRequest;
import ai.deepdetect.dto.request.UpdateHistoryRequest;
import ai.deepdetect.entities.HistoryEntity;
import ai.deepdetect.exceptions.RecordNotFoundException;
import ai.deepdetect.repositories.HistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryEntity addHistoryRecord(NewHistoryRequest historyRequest) {
        HistoryEntity entity = new HistoryEntity();
        BeanUtils.copyProperties(historyRequest, entity);
        entity.setStatus("PENDING");

        return historyRepository.save(entity);
    }
    
    public HistoryEntity updateHistoryRecord(UpdateHistoryRequest historyRequest) throws RecordNotFoundException {
        HistoryEntity entity = getRecordByRequestId(historyRequest.getRequestId());
        BeanUtils.copyProperties(historyRequest, entity);
        entity.setStatus("COMPLETED");
        entity.setEndDate(new Date());

        return historyRepository.save(entity);
    }

    public List<HistoryEntity> getAllRecords() {
        return historyRepository.findAll();
    }
    
    public List<HistoryEntity> getAllUserRecords(int userId) {
        return historyRepository.findByUserId(userId);
    }
    
    public HistoryEntity getRecord(int id) throws RecordNotFoundException {
        HistoryEntity byId = historyRepository.findById(id);

        if (byId == null)
            throw new RecordNotFoundException();

        return byId;
    }
    
    public HistoryEntity getRecordByRequestId(String id) throws RecordNotFoundException {
        HistoryEntity byId = historyRepository.findByRequestId(id);

        if (byId == null)
            throw new RecordNotFoundException();

        return byId;
    }
    
    public HistoryEntity deleteRecord(int id) throws RecordNotFoundException {
        HistoryEntity byId = getRecord(id);

        if (byId == null)
            throw new RecordNotFoundException();

        historyRepository.delete(byId);
        return byId;
    }
    
}
