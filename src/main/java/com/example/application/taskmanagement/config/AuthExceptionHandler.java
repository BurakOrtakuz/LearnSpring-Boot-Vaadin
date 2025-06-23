package com.example.application.taskmanagement.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.application.taskmanagement.auth")
public class AuthExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException exception)
    {
        System.out.println("Access denied: " + exception.getMessage());
        return ResponseEntity.status(403).body("Erişim reddedildi: " + exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception exception)
    {
        System.out.println("An error occurred: " + exception.getMessage());
        return ResponseEntity.status(500).body("Bir hata oluştu: " + exception.getMessage());
    }
}
