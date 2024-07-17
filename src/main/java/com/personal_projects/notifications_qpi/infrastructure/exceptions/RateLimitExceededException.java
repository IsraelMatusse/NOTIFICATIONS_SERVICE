package com.personal_projects.notifications_qpi.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RateLimitExceededException extends Exception {
    public RateLimitExceededException(String message){
        super(message);
    }
}
