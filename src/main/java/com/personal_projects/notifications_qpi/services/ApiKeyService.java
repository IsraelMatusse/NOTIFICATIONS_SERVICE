package com.personal_projects.notifications_qpi.services;

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

    public String createApiKey(){
        String key= ApiKeyGenerator.generateApiKey();
        String hashedKey=passwordEncoder.encode(key);
        LocalDateTime expirationDate=LocalDateTime.now().plusDays(1);
        ApiKey apiKey= new ApiKey(hashedKey, expirationDate);
        this.create(apiKey);
        return  key;
    }
    public boolean validateKey(String rawApiKey) {
        return apiKeyRepo.findAll().stream()
                .anyMatch(apiKey -> passwordEncoder.matches(rawApiKey, apiKey.getApiKeyValue()));
    }

}
