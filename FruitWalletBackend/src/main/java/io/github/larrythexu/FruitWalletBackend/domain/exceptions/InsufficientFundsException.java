package io.github.larrythexu.FruitWalletBackend.domain.exceptions;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;

public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(Account account, Origin currency, float amount) {
    super(
        "Account balance insufficient, "
            + currency.toString()
            + " amount: "
            + account.getWallet().getBalanceFromOrigin(currency)
            + ", requested amount: "
            + amount);
  }
}
