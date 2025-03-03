package org.example.history_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.history_service.model.History;
import org.example.history_service.service.IHistoryService;
import org.example.history_service.service.impl.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final IHistoryService historyService;

    // Fetch history by customerId
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<History>> getHistoryByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(historyService.getHistoryByCustomerId(customerId));
    }

    // Fetch history by accountId
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<History>> getHistoryByAccountId(@PathVariable Integer accountId) {
        return ResponseEntity.ok(historyService.getHistoryByAccountId(accountId));
    }

    // Fetch all history records (Optional)
    @GetMapping("/all")
    public ResponseEntity<List<History>> getAllHistory() {
        return ResponseEntity.ok(historyService.getAllHistory());
    }


}
