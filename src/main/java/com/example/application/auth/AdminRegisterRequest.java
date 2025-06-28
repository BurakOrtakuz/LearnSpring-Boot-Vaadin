package com.example.application.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminRegisterRequest extends RegisterRequest{
    private String roleName;
}
