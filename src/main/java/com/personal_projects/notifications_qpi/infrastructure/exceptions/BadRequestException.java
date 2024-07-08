package com.personal_projects.notifications_qpi.infrastructure.exceptions;

public class BadRequestException extends Exception {
    public BadRequestException(String message){
        super(message);
    }
}
