package org.example.history_service.dao;

import org.example.history_service.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

    // Find history logs by customerId
    List<History> findByCustomerId(Integer customerId);

    // Find history logs by accountId
    List<History> findByAccountId(Integer accountId);
}
