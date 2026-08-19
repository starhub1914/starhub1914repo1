package com.cth.sdm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doc_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String docId; // e.g. P101, P102, P201...

    @Column(nullable = false)
    private Integer phaseNumber; // 1 to 7

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String appCode; // 3-char code, e.g. SDM

    private String versionNum; // e.g. 1.0.0
    private String docCode;

    private String fileName;
    private String filePath;
    private String fileType;

    @Column(nullable = false)
    private String status; // PENDING_MAKER, PENDING_APPROVAL, APPROVED, REJECTED

    private String makerUsername;
    private String checkerUsername;

    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;

    private String aiStatus; // UNPROCESSED, IN_PROGRESS, COMPLETED, FAILED

    @Column(columnDefinition = "TEXT")
    private String aiRecommendation;
}
