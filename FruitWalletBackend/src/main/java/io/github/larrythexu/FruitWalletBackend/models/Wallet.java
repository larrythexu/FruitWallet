package io.github.larrythexu.FruitWalletBackend.models;

import jakarta.persistence.Embeddable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Wallet {
  private float appleBalance;
  private float bananaBalance;
  private float orangeBalance;

  public float getBalanceFromIndex(int index) {
    List<Float> balances = List.of(appleBalance, bananaBalance, orangeBalance);
    if (index < 0 || index >= balances.size()) {
      throw new IllegalArgumentException("Invalid wallet index: " + index);
    }

    return balances.get(index);
  }
}
