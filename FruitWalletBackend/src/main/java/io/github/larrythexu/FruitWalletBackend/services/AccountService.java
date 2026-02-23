package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.domain.exceptions.AccountNotFoundException;
import io.github.larrythexu.FruitWalletBackend.domain.exceptions.UsernameAlreadyExistsException;
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

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createAccount(String username) {
    if (accountRepository.findByUsername(username).isPresent()) {
      throw new UsernameAlreadyExistsException(username);
    }
    // Assign origin on account creation
    int originEnum = COUNTER.intValue() % 3;
    Origin newOrigin = Origin.ofIndex(originEnum).orElse(Origin.APPLE);

    Account newAccount = new Account(username, newOrigin);
    accountRepository.save(newAccount);

    // Increment account counter
    COUNTER.incrementAndGet();

    return newAccount;
  }

  public Account getAccountByID(long id) {
    return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
  }

  public Account getAccountByUsername(String username) {
    return accountRepository
        .findByUsername(username)
        .orElseThrow(() -> new AccountNotFoundException(username));
  }
}
