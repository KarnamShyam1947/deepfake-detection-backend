package ai.deepdetect.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.dto.request.NewHistoryRequest;
import ai.deepdetect.dto.request.UpdateHistoryRequest;
import ai.deepdetect.entities.HistoryEntity;
import ai.deepdetect.exceptions.RecordNotFoundException;
import ai.deepdetect.services.HistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final HistoryService historyService;
    
    @PostMapping
    public ResponseEntity<HistoryEntity> handleNewRequest(@RequestBody NewHistoryRequest request) {
        HistoryEntity historyRecord = historyService.addHistoryRecord(request);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(historyRecord);
    }
    
    @PutMapping
    public ResponseEntity<HistoryEntity> handleUpdateRequest(@RequestBody UpdateHistoryRequest request) throws RecordNotFoundException {
        HistoryEntity historyRecord = historyService.updateHistoryRecord(request);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(historyRecord);
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<HistoryEntity>> getAllRecordsByUserId(@PathVariable("user-id") int userId) {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(historyService.getAllUserRecords(userId));
    }
    
    @GetMapping
    public ResponseEntity<List<HistoryEntity>> getAllRecords() {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(historyService.getAllRecords());
    }
    
    
    @GetMapping("/request/{request-id}")
    public ResponseEntity<HistoryEntity> getAllRecordsById(@PathVariable("request-id") String requestId) throws RecordNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(historyService.getRecordByRequestId(requestId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HistoryEntity> getAllRecordsById(@PathVariable("id") int id) throws RecordNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(historyService.getRecord(id));
    }
}
