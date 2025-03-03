package org.example.history_service.service;

import org.example.history_service.model.History;
import org.example.history_service.model.dto.HistoryDTO;

import java.util.List;

public interface IHistoryService {
    void listenHistoryEvents(HistoryDTO historyDTO);
    List<History> getHistoryByCustomerId(Integer customerId);
    List<History> getHistoryByAccountId(Integer accountId);
    List<History> getAllHistory();
}