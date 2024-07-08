package com.personal_projects.notifications_qpi.controllers;

import com.personal_projects.notifications_qpi.dtos.internal.ResponseAPI;
import com.personal_projects.notifications_qpi.dtos.request.ValidApiKeyDTO;
import com.personal_projects.notifications_qpi.services.ApiKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-key")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAPI> generateApiKey() {
        String apiKey=apiKeyService.createApiKey();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseAPI("Api key generated sucessfully", apiKey));
    }

    @PutMapping(value = "/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAPI> valiApiKey(@RequestBody  ValidApiKeyDTO apiKeyData) {
        boolean valid=apiKeyService.validateKey(apiKeyData.apiKey());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseAPI("Api key is status", valid));
    }
}
