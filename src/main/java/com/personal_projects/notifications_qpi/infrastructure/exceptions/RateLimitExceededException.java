package com.personal_projects.notifications_qpi.infrastructure.exceptions;

public class RateLimitExceededException extends Exception {
    public RateLimitExceededException(String message){
        super(message);
    }
}
