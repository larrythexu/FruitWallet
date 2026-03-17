package io.github.larrythexu.FruitWalletBackend.domain.exceptions;

public class InvalidAmountException extends RuntimeException {
  public InvalidAmountException(float amount) {
    super("Invalid amount (must be greater than 0): " + amount);
  }
}
