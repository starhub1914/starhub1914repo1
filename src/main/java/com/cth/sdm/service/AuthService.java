package com.cth.sdm.service;

import com.cth.sdm.model.AuditLog;
import com.cth.sdm.model.User;
import com.cth.sdm.repository.AuditLogRepository;
import com.cth.sdm.repository.SystemConfigRepository;
import com.cth.sdm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SystemConfigRepository systemConfigRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       SystemConfigRepository systemConfigRepository,
                       AuditLogRepository auditLogRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.systemConfigRepository = systemConfigRepository;
        this.auditLogRepository = auditLogRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isLdapEnabled() {
        return systemConfigRepository.findByConfigKey("AUTH_MODE")
                .map(c -> "LDAP".equalsIgnoreCase(c.getConfigValue()))
                .orElse(false);
    }

    public boolean authenticate(String username, String password) {
        if (isLdapEnabled()) {
            // Simulated LDAP active directory check
            boolean ldapSuccess = "admin".equalsIgnoreCase(username) || "maker1".equalsIgnoreCase(username) || "checker1".equalsIgnoreCase(username);
            auditLogRepository.save(AuditLog.builder()
                    .action("LDAP_LOGIN")
                    .username(username)
                    .details("LDAP Authentication " + (ldapSuccess ? "SUCCESS" : "FAILED"))
                    .status(ldapSuccess ? "SUCCESS" : "FAILED")
                    .build());
            return ldapSuccess;
        }

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        if (Boolean.TRUE.equals(user.getAccountLocked())) {
            auditLogRepository.save(AuditLog.builder()
                    .action("LOGIN_ATTEMPT")
                    .username(username)
                    .details("Login failed: Account is locked")
                    .status("FAILED")
                    .build());
            return false;
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            user.setFailedAttempts(0);
            userRepository.save(user);
            auditLogRepository.save(AuditLog.builder()
                    .action("LOCAL_LOGIN")
                    .username(username)
                    .details("User logged in successfully")
                    .status("SUCCESS")
                    .build());
            return true;
        } else {
            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);
            if (attempts >= 5) {
                user.setAccountLocked(true);
            }
            userRepository.save(user);
            auditLogRepository.save(AuditLog.builder()
                    .action("LOCAL_LOGIN")
                    .username(username)
                    .details("Login failed. Failed attempts: " + attempts)
                    .status("FAILED")
                    .build());
            return false;
        }
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);

        auditLogRepository.save(AuditLog.builder()
                .action("CREATE_USER")
                .username(user.getUsername())
                .details("Account created for " + user.getRole())
                .status("SUCCESS")
                .build());

        return saved;
    }

    public void lockUser(String username, boolean lock) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setAccountLocked(lock);
            if (!lock) user.setFailedAttempts(0);
            userRepository.save(user);
            auditLogRepository.save(AuditLog.builder()
                    .action(lock ? "LOCK_USER" : "UNLOCK_USER")
                    .username(username)
                    .details("User lock status changed to " + lock)
                    .status("SUCCESS")
                    .build());
        });
    }

    public void resetPassword(String username, String newPassword) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPasswordExpired(false);
            userRepository.save(user);
            auditLogRepository.save(AuditLog.builder()
                    .action("RESET_PASSWORD")
                    .username(username)
                    .details("Password reset successfully")
                    .status("SUCCESS")
                    .build());
        });
    }
}
