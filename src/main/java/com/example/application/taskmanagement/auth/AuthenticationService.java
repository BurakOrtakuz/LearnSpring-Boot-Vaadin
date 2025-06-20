package com.example.application.taskmanagement.auth;

import com.example.application.taskmanagement.config.JwtService;
import com.example.application.taskmanagement.domain.Person;
import com.example.application.taskmanagement.repository.IPersonRepository;

import com.example.application.taskmanagement.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IPersonRepository personRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService JwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        System.out.println("Registering user: " + request.getRoleName());
        var role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleName()));
        System.out.println("Found role: " + role);


        var user = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        personRepository.save(user);
        var jwtToken = JwtService.generateToken(user);
        return RegisterResponse.builder().
                token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = personRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = JwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken)
                .role(user.getRole().getName())
                .build();
    }
}
