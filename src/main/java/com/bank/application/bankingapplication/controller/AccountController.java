package com.bank.application.bankingapplication.controller;

import com.bank.application.bankingapplication.dto.AccountDto;
import com.bank.application.bankingapplication.dto.Amount;
import com.bank.application.bankingapplication.security.LoginRequest;
import com.bank.application.bankingapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AccountController(AccountService accountService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> depositAmount(@PathVariable Long id, @RequestBody Amount amount) {
        AccountDto accountDto = accountService.depositAmount(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawAmount(@PathVariable Long id, @RequestBody Amount amount) {
        AccountDto accountDto = accountService.withdrawAmount(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accountDtos = accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account deleted successfully.");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("User Authenticated!");
    }

}
