package com.personal_projects.notifications_qpi.infrastructure.exceptions;

public class InternalServerErrorException extends Exception {
    public InternalServerErrorException(String message){
        super(message);
    }
}
