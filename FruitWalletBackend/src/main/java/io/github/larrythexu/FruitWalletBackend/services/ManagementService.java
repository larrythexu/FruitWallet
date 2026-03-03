package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.dtos.ClaimDTO;
import io.github.larrythexu.FruitWalletBackend.dtos.DTOMapper;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
/** Handles general management of account actions */
public class ManagementService {

  private final AccountService accountService;
  private final FactoryService factoryService;
  private final DTOMapper dTOMapper;

  public ManagementService(
      AccountService accountService, FactoryService factoryService, DTOMapper dTOMapper) {
    this.accountService = accountService;
    this.factoryService = factoryService;
    this.dTOMapper = dTOMapper;
  }

  public Account signupAccount(String username) {
    Account newAccount = accountService.createAccount(username);
    // Create default factory
    Factory starterFactory =
        factoryService.createDefaultFactory(newAccount, newAccount.getOrigin());
    newAccount.addFactory(starterFactory);

    return newAccount;
  }

  @Transactional
  public List<ClaimDTO> computeClaim(long id) {
    Account account = accountService.getAccountByID(id);
    Instant time = Instant.now();

    List<ClaimDTO> claims = new ArrayList<>();

    for (Factory f : account.getFactoryList()) {
      ClaimDTO curClaim = factoryService.claimFactoryPoints(f, time);
      account.getWallet().addBalance(f.getOrigin(), curClaim.balance());
      claims.add(curClaim);
    }

    return claims;
  }
}
