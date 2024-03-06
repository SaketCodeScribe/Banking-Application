package com.bank.application.bankingapplication.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    String username;
    String password;
}
