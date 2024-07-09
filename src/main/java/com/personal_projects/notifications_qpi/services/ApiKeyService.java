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

    public String createApiKey(){
        String key= ApiKeyGenerator.generateApiKey();
        String hashedKey=passwordEncoder.encode(key);
        String uuid = UUID.randomUUID().toString(); // Gerar UUID
        LocalDateTime expirationDate=LocalDateTime.now().plusDays(1);
        ApiKey apiKey= new ApiKey(hashedKey, expirationDate, uuid);
        this.create(apiKey);
        return uuid + ":" + key; // Retorna UUID concatenado com a chave
    }
    public boolean validateKey(String rawApiKeyWithUuid) {
        // Separar o UUID da chave original
        String[] parts = rawApiKeyWithUuid.split(":");
        if (parts.length != 2) {
            return false;
        }
        String uuid = parts[0];
        String rawApiKey = parts[1];

        // Buscar a chave pelo UUID
        Optional<ApiKey> apiKeyOpt = this.apiKeyRepo.findById(uuid);
        if (apiKeyOpt.isEmpty()) {
            return false;
        }

        ApiKey apiKey = apiKeyOpt.get();
        // Validar a chave hasheada
        return passwordEncoder.matches(rawApiKey, apiKey.getApiKeyValue());
    }


}
