package com.cth.sdm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String action;
    private String username;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String status;

    @PrePersist
    public void onPrePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
