package com.personal_projects.notifications_qpi.services;

import com.personal_projects.notifications_qpi.dtos.request.ApiKeyCreateDTO;
import com.personal_projects.notifications_qpi.entities.ApiKey;
import com.personal_projects.notifications_qpi.infrastructure.utils.ApiKeyGenerator;
import com.personal_projects.notifications_qpi.repositories.ApiKeyRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApiKeyService {

    @Value("${SECRET_KEY}")
    private  String secret;
    private final String issuer = "notification-service";
    private final ApiKeyRepo apiKeyRepo;
    private final PasswordEncoder passwordEncoder;

    public ApiKeyService(ApiKeyRepo apiKeyRepo, @Lazy  PasswordEncoder passwordEncoder) {
        this.apiKeyRepo = apiKeyRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public ApiKey create(ApiKey apiKey){
        return apiKeyRepo.save(apiKey);
    }

    public ApiKey findById(String id){
        return apiKeyRepo.findById(id).orElseThrow(()-> new RuntimeException("Api key not found"));
    }

    public String createApiKey(ApiKeyCreateDTO apiKeyData){
        String key= ApiKeyGenerator.generateApiKey();
        String hashedKey=passwordEncoder.encode(key);
        String uuid = UUID.randomUUID().toString();
        LocalDateTime expirationDate=LocalDateTime.now().plusDays(1);
        String userEmail=apiKeyData.email();
        ApiKey apiKey= new ApiKey(hashedKey, expirationDate, uuid, userEmail);
        this.create(apiKey);
        return uuid + ":" + key;
    }

    public ApiKey getApiKeyFromRawApiKey(String rawApiKeyWithUuid){
        String[] parts = rawApiKeyWithUuid.split(":");
        if (parts.length != 2) {
            return null;
        }
        String uuid = parts[0];
        Optional<ApiKey> apiKeyOpt = this.apiKeyRepo.findById(uuid);
        return apiKeyOpt.orElse(null);
    }

    public boolean validateKey(String rawApiKeyWithUuid) {
        String[] parts = rawApiKeyWithUuid.split(":");
        if (parts.length != 2) {
            return false;
        }
        String uuid = parts[0];
        String rawApiKey = parts[1];

        Optional<ApiKey> apiKeyOpt = this.apiKeyRepo.findById(uuid);
        if (apiKeyOpt.isEmpty()) {
            return false;
        }

        ApiKey apiKey = apiKeyOpt.get();
        return apiKey.getExpirationDate().isAfter(LocalDateTime.now()) && passwordEncoder.matches(rawApiKey, apiKey.getApiKeyValue());
    }


}
