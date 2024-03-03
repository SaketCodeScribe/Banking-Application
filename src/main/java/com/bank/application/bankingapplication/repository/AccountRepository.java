package com.bank.application.bankingapplication.repository;

import com.bank.application.bankingapplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
