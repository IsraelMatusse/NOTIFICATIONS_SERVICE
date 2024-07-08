package com.personal_projects.notifications_qpi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "api_key")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String apiKeyValue;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;
    private boolean status;

    public ApiKey(String apiKeyValue, LocalDateTime expirationDate){
        this.apiKeyValue = apiKeyValue;
        this.expirationDate=expirationDate;
        this.createdAt=LocalDateTime.now();
        this.status=true;
    }

}
