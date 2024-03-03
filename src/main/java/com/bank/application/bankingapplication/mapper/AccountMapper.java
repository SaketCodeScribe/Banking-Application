package com.bank.application.bankingapplication.mapper;

import com.bank.application.bankingapplication.dto.AccountDto;
import com.bank.application.bankingapplication.entity.Account;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance().setScale(2, RoundingMode.HALF_EVEN)
        );
        return account;
    }

    public static AccountDto maptoAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance().setScale(2, RoundingMode.HALF_EVEN)
        );
        return accountDto;
    }
}
