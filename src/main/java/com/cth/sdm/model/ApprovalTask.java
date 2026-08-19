package com.cth.sdm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approval_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docId;
    private String makerUsername;
    private String checkerUsername;
    private String action; // SUBMIT, APPROVE, REJECT
    private String comments;
    private LocalDateTime actionTimestamp;

    @PrePersist
    public void onPrePersist() {
        if (actionTimestamp == null) {
            actionTimestamp = LocalDateTime.now();
        }
    }
}
