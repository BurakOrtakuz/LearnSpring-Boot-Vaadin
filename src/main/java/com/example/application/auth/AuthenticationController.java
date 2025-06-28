package com.example.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Registering user {0}", request.getUsername());
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adminRegister")
    public ResponseEntity<AdminRegisterResponse> register(@RequestBody AdminRegisterRequest request)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AdminRegistering user {0}", request.getUsername());
        return ResponseEntity.ok(authenticationService.adminRegister(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Authenticating user {0}", request.getUsername());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
