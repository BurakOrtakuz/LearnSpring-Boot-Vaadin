package com.example.application.taskmanagement.security;

import com.example.application.taskmanagement.domain.Person;
import com.example.application.taskmanagement.repository.IPersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IPersonRepository personRepository;

    public CustomUserDetailsService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + person.getRole().getName().toUpperCase());
        return new User(person.getUsername(), person.getPassword(), Collections.singleton(authority));
    }
}

