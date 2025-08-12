package ai.deepdetect.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @CachePut(value = "historyRequestId", key = "#result.requestId")
    @CacheEvict(value = "historyList", allEntries = true)
    public HistoryEntity addHistoryRecord(NewHistoryRequest historyRequest) {
        HistoryEntity entity = new HistoryEntity();
        BeanUtils.copyProperties(historyRequest, entity);
        entity.setStatus("PENDING");

        return historyRepository.save(entity);
    }
    
    @CachePut(value = "historyRequestId", key = "#result.requestId")
    @CacheEvict(value = "historyList", allEntries = true)
    public HistoryEntity updateHistoryRecord(UpdateHistoryRequest historyRequest) throws RecordNotFoundException {
        HistoryEntity entity = getRecordByRequestId(historyRequest.getRequestId());
        BeanUtils.copyProperties(historyRequest, entity);
        entity.setStatus("COMPLETED");
        entity.setEndDate(new Date());

        return historyRepository.save(entity);
    }

    @Cacheable(value = "historyList")
    public List<HistoryEntity> getAllRecords() {
        return historyRepository.findAll();
    }
    
    @Cacheable(value = "historyUserId", key = "#userId")
    public List<HistoryEntity> getAllUserRecords(int userId) {
        return historyRepository.findByUserId(userId);
    }
    
    @Cacheable(value = "historyId", key = "#id")
    public HistoryEntity getRecord(int id) throws RecordNotFoundException {
        HistoryEntity byId = historyRepository.findById(id);

        if (byId == null)
            throw new RecordNotFoundException();

        return byId;
    }
    
    @Cacheable(value = "historyRequestId", key = "#id")
    public HistoryEntity getRecordByRequestId(String id) throws RecordNotFoundException {
        HistoryEntity byId = historyRepository.findByRequestId(id);

        if (byId == null)
            throw new RecordNotFoundException();

        return byId;
    }
    
    @CacheEvict(value = "historyId", key = "#id")
    public HistoryEntity deleteRecord(int id) throws RecordNotFoundException {
        HistoryEntity byId = getRecord(id);

        if (byId == null)
            throw new RecordNotFoundException();

        historyRepository.delete(byId);
        return byId;
    }
    
}
