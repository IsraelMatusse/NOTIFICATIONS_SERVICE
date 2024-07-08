package com.personal_projects.notifications_qpi.services;

import com.personal_projects.notifications_qpi.entities.ApiKey;
import com.personal_projects.notifications_qpi.infrastructure.utils.ApiKeyGenerator;
import com.personal_projects.notifications_qpi.repositories.ApiKeyRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApiKeyService {

    @Value("${SECRET_KEY}")
    private  String secret;
    private final String issuer = "notification-service";
    private final ApiKeyRepo apiKeyRepo;


    public ApiKeyService(ApiKeyRepo apiKeyRepo) {
        this.apiKeyRepo = apiKeyRepo;
    }

    public ApiKey create(ApiKey apiKey){
        return apiKeyRepo.save(apiKey);
    }

    public ApiKey findById(String id){
        return apiKeyRepo.findById(id).orElseThrow(()-> new RuntimeException("Api key not found"));
    }

    public String createApiKey(){
        String key= ApiKeyGenerator.generateApiKey();
        LocalDateTime expirationDate=LocalDateTime.now().plusDays(1);
        ApiKey apiKey= new ApiKey(key, expirationDate);
        this.create(apiKey);
        return  key;
    }
    public boolean validateKey(String apiKey) {
        return apiKeyRepo.existsByApiKeyValueAndExpirationDateAfter(apiKey, LocalDateTime.now());
    }

}
