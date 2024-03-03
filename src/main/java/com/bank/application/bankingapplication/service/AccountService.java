package com.bank.application.bankingapplication.service;

import com.bank.application.bankingapplication.dto.AccountDto;
import com.bank.application.bankingapplication.dto.Amount;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto depositAmount(Long id, Amount amount);
    AccountDto withdrawAmount(Long id, Amount amount);
    List<AccountDto> getAllAccounts();
    void deleteAccountById(Long id);
}
