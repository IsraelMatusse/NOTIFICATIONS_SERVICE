package com.personal_projects.notifications_qpi.infrastructure.exceptions;

public class UnprocessableEntityException extends Exception {
    public UnprocessableEntityException(String message){
        super(message);
    }
}
