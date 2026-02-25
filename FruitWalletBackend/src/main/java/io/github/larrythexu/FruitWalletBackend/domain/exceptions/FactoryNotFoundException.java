package io.github.larrythexu.FruitWalletBackend.domain.exceptions;

public class FactoryNotFoundException extends RuntimeException {
  public FactoryNotFoundException(long id) {
    super("Factory not found with ID: " + id);
  }
}
