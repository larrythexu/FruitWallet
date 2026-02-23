package io.github.larrythexu.FruitWalletBackend.domain.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String username) {
    super("Username already exists: " + username);
  }
}
