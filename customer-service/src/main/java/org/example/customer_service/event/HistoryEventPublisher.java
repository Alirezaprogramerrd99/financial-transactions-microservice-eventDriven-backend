package org.example.customer_service.event;

import lombok.RequiredArgsConstructor;
import org.example.customer_service.model.dto.HistoryDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryEventPublisher {

    private final KafkaTemplate<String, HistoryDTO> kafkaTemplate;

    public void publishHistoryEvent(HistoryDTO historyDTO) {
        kafkaTemplate.send("history-topic", historyDTO);
    }
}