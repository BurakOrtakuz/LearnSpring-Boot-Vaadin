package com.example.application.auth;

import com.example.application.config.JwtService;
import com.example.application.domain.Person;
import com.example.application.domain.Patient;
import com.example.application.domain.Role;
import com.example.application.repository.IPatientRepository;
import com.example.application.repository.IPersonRepository;
import com.example.application.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IPersonRepository personRepository;
    private final IPatientRepository patientRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService JwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${application.default.role.name}")
    private String defaultRoleName;

    public RegisterResponse register(RegisterRequest request) {
        Role role = roleRepository.findByName(defaultRoleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + defaultRoleName));

        Person person = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthDate(LocalDate.parse(request.getBirthDate()))
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(role)
                .build();

        Patient patient = new Patient();
        patient.setPerson(person);
        patientRepository.save(patient);

        var jwtToken = JwtService.generateToken(person);
        return RegisterResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AdminRegisterResponse adminRegister(AdminRegisterRequest request)
    {
        Role role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleName()));

        Person user = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        personRepository.save(user);
        return AdminRegisterResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roleName(user.getRole().getName())
                .message("User registered successfully")
                .build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Person user = personRepository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = JwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken)
                .role(user.getRole().getName())
                .build();
    }

}
