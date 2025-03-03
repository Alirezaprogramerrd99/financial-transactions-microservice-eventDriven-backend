package org.example.history_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.history_service.dao.HistoryRepository;
import org.example.history_service.model.History;
import org.example.history_service.model.dto.HistoryDTO;
import org.example.history_service.service.IHistoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor  // for dependancyInjection (for constructors)
public class HistoryService implements IHistoryService {

    private final HistoryRepository historyRepository;

    @KafkaListener(topics = "history-topic", groupId = "history-service-group")
    public void listenHistoryEvents(HistoryDTO historyDTO) {
        History history = new History();
        history.setUsername(historyDTO.getUsername());
        history.setRegistrationDate(historyDTO.getRegistrationDate());
        history.setDescription(historyDTO.getDescription());
        history.setCustomerId(historyDTO.getCustomerId());
        history.setAccountId(historyDTO.getAccountId());

        historyRepository.save(history);
    }

    // Get history records by customerId
    public List<History> getHistoryByCustomerId(Integer customerId) {
        return historyRepository.findByCustomerId(customerId);
    }

    // Get history records by accountId
    public List<History> getHistoryByAccountId(Integer accountId) {
        return historyRepository.findByAccountId(accountId);
    }

    // Get all history records
    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

}
