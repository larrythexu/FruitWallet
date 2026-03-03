package io.github.larrythexu.FruitWalletBackend.domain.exceptions;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;

public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(Origin currency, float amount, float currentBalance) {
    super(
        "Account balance insufficient, "
            + currency.toString()
            + " amount: "
            + currentBalance
            + ", requested amount: "
            + amount
            + ". Missing: "
            + (amount - currentBalance));
  }
}
