package com.personal_projects.notifications_qpi.infrastructure.exceptions;

import com.personal_projects.notifications_qpi.dtos.internal.ResponseAPI;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class Handler {
    private final Logger logger = LoggerFactory.getLogger(Handler.class);
    private final Map<String, String> errors = new HashMap<>();

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseAPI handleUsernameNotFound(HttpServletRequest request, UsernameNotFoundException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseAPI handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseAPI("Erro de Validação!",  errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseAPI handleBadRequest(HttpServletRequest request, BadRequestException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ResponseAPI handleConflict(HttpServletRequest request, ConflictException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseAPI handleForbidden(HttpServletRequest request, ForbiddenException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseAPI handleInternalServerError(HttpServletRequest request, InternalServerErrorException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseAPI handleNotFound(HttpServletRequest request, NotFoundException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseAPI handleUnauthorized(HttpServletRequest request, UnauthorizedException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseAPI handleUE(HttpServletRequest request, UnprocessableEntityException ex) {
        return new ResponseAPI(ex.getMessage(),  null);
    }




}
