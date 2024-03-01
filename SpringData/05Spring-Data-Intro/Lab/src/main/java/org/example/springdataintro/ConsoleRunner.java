package org.example.springdataintro;

import org.example.springdataintro.models.Account;
import org.example.springdataintro.models.User;
import org.example.springdataintro.services.AccountService;
import org.example.springdataintro.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private AccountService accountService;

    private UserService userService;

    public ConsoleRunner(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Pesho", 20);
        userService.register(user);

        Account account = new Account(BigDecimal.valueOf(25000), user);
        this.accountService.createAccount(account);

        accountService.depositMoney(BigDecimal.TEN, 1L);
        accountService.withdrawMoney(BigDecimal.valueOf(20000), account.getId());
        accountService.withdrawMoney(BigDecimal.valueOf(10000), account.getId());
    }
}
