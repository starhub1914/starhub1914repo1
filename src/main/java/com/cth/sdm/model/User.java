package com.cth.sdm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private String email;
    private String mobile;

    @Column(nullable = false)
    private String role; // ADMIN, MAKER, CHECKER

    @Builder.Default
    private Boolean accountLocked = false;

    @Builder.Default
    private Boolean passwordExpired = false;

    @Builder.Default
    private Integer failedAttempts = 0;

    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
