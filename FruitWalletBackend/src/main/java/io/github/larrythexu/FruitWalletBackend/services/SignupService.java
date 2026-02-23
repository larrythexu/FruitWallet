package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignupService {

  private final AccountService accountService;
  private final FactoryService factoryService;

  public SignupService(AccountService accountService, FactoryService factoryService) {
    this.accountService = accountService;
    this.factoryService = factoryService;
  }

  public Account signupAccount(String username) {
    Account newAccount = accountService.createAccount(username);
    // Create default factory
    Factory starterFactory =
        factoryService.createDefaultFactory(newAccount, newAccount.getOrigin());
    newAccount.addFactory(starterFactory);

    return newAccount;
  }
}
