package com.personal_projects.notifications_qpi.infrastructure.config;

import lombok.Data;

@Data
public class Failure implements Type {
    private final String message;
}
