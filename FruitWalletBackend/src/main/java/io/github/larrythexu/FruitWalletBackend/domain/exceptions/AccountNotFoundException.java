package io.github.larrythexu.FruitWalletBackend.domain.exceptions;

public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(String username) {
    super("Account not found with username: " + username);
  }

  public AccountNotFoundException(long id) {
    super("Account not found with ID: " + id);
  }
}
