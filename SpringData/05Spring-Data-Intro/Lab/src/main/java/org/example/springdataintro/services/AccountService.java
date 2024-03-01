package org.example.springdataintro.services;

import org.example.springdataintro.models.Account;

import java.math.BigDecimal;

public interface AccountService {
    
    void createAccount(Account account);
    
    void withdrawMoney(BigDecimal money, Long id);

    void depositMoney(BigDecimal money, Long id);
}
