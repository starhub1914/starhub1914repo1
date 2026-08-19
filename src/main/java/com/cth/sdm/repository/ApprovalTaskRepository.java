package com.cth.sdm.repository;

import com.cth.sdm.model.ApprovalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApprovalTaskRepository extends JpaRepository<ApprovalTask, Long> {
    List<ApprovalTask> findByDocIdOrderByActionTimestampDesc(String docId);
}
