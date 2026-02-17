package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService {

  private final AccountRepository accountRepository;

  private static final AtomicInteger COUNTER = new AtomicInteger(0);
  private static final double START_BALANCE = 0.0;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createAccount(String username) {
    if (accountRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("This username already exists");
    }
    // Assign origin on account creation
    int originEnum = COUNTER.intValue() % 3;
    Origin newOrigin = Origin.ofIndex(originEnum).orElse(Origin.APPLE);

    Account newAccount =
        Account.builder()
            .username(username)
            .origin(newOrigin)
            .appleBalance(START_BALANCE)
            .bananaBalance(START_BALANCE)
            .orangeBalance(START_BALANCE)
            .build();
    accountRepository.save(newAccount);

    // Increment account counter
    COUNTER.incrementAndGet();

    return newAccount;
  }
}
