package io.github.larrythexu.FruitWalletBackend.controllers;

import io.github.larrythexu.FruitWalletBackend.dtos.AccountDTO;
import io.github.larrythexu.FruitWalletBackend.dtos.CreateUserReq;
import io.github.larrythexu.FruitWalletBackend.dtos.DTOMapper;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.services.ManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ManagementController {

  private final ManagementService managementService;
  private final DTOMapper dtoMapper;

  // TODO: add more to this once we have oauth
  @PostMapping("/signup")
  public AccountDTO signupAccount(@RequestBody CreateUserReq createUserReq) {
    Account account = managementService.signupAccount(createUserReq.username());
    return dtoMapper.toAccountDTO(account);
  }

//  @PostMapping("/manage/claim-all")
}
