package com.cth.sdm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doc_versions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docId;
    private String versionNum;
    private String fileName;
    private String filePath;
    private String uploadedBy;
    private LocalDateTime uploadedAt;

    @PrePersist
    public void onPrePersist() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }
}
