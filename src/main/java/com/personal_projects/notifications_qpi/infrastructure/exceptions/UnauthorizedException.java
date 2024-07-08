package com.personal_projects.notifications_qpi.infrastructure.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message){
        super(message);
    }
}
