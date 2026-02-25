package io.github.larrythexu.FruitWalletBackend.controllers;

import io.github.larrythexu.FruitWalletBackend.dtos.AccountDTO;
import io.github.larrythexu.FruitWalletBackend.dtos.DTOMapper;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AccountController {

  private final AccountService accountService;
  private final DTOMapper dtoMapper;

  @GetMapping("/account/{id}")
  public AccountDTO getAccount(@PathVariable long id) {
    Account account = accountService.getAccountByID(id);
    return dtoMapper.toAccountDTO(account);
  }

  @GetMapping("/account/{username}")
  public AccountDTO getAccount(@PathVariable String username) {
    Account account = accountService.getAccountByUsername(username);
    return dtoMapper.toAccountDTO(account);
  }
}
