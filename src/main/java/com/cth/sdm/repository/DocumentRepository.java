package com.cth.sdm.repository;

import com.cth.sdm.model.DocumentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentItem, Long> {
    Optional<DocumentItem> findByDocId(String docId);
    List<DocumentItem> findByPhaseNumber(Integer phaseNumber);
    List<DocumentItem> findByStatus(String status);
}
