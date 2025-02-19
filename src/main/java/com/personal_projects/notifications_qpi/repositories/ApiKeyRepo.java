package com.personal_projects.notifications_qpi.repositories;

import com.personal_projects.notifications_qpi.entities.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface ApiKeyRepo extends JpaRepository<ApiKey, String> {

    ApiKey findByApiKeyValueAndExpirationDateAfter(String key, LocalDateTime expirationDate);
    boolean existsByApiKeyValueAndExpirationDateAfter(String key, LocalDateTime expirationDate);
    Optional<ApiKey> findByApiKeyValue(String key);
}
