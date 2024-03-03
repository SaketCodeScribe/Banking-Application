package com.bank.application.bankingapplication.service.impl;

import com.bank.application.bankingapplication.dto.AccountDto;
import com.bank.application.bankingapplication.dto.Amount;
import com.bank.application.bankingapplication.entity.Account;
import com.bank.application.bankingapplication.mapper.AccountMapper;
import com.bank.application.bankingapplication.repository.AccountRepository;
import com.bank.application.bankingapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        AccountDto savedAccountDto = AccountMapper.maptoAccountDto(savedAccount);
        return savedAccountDto;
    }

    @Override
    public AccountDto getAccountById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exists."));
        AccountDto accountDto = AccountMapper.maptoAccountDto(account);
        return accountDto;
    }

    @Override
    public AccountDto depositAmount(Long id, Amount amount) {
        if (amount.getAmount() == null)
            throw new RuntimeException("Amount can't be null.");
        AccountDto accountDto = getAccountById(id);
        accountDto.setBalance(accountDto.getBalance().add(amount.getAmount()).setScale(2, RoundingMode.HALF_EVEN));
        Account savedAccount = AccountMapper.mapToAccount(accountDto);
        accountRepository.save(savedAccount);
        return accountDto;
    }

    @Override
    public AccountDto withdrawAmount(Long id, Amount amount) {
        if (amount.getAmount() == null)
            throw new RuntimeException("Amount can't be null");
        AccountDto accountDto = getAccountById(id);
        if (accountDto.getBalance().compareTo(amount.getAmount()) < 0)
            throw new RuntimeException("Insufficient Balance.");
        accountDto.setBalance(accountDto.getBalance().subtract(amount.getAmount()).setScale(2, RoundingMode.HALF_EVEN));
        Account savedAccount = AccountMapper.mapToAccount(accountDto);
        accountRepository.save(savedAccount);
        return accountDto;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> AccountMapper.maptoAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccountById(Long id) {
        AccountDto accountDto = getAccountById(id);
        accountRepository.deleteById(id);
    }
}
