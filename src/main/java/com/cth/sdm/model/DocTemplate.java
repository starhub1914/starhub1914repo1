package com.cth.sdm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doc_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String templateName;

    private String fileType; // EXCEL, WORD, PPT
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
