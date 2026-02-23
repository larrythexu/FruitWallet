package io.github.larrythexu.FruitWalletBackend.models;

import jakarta.persistence.Embeddable;
import java.util.List;
import lombok.*;

@Data
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
  private float appleBalance = 0.0f;
  private float bananaBalance = 0.0f;
  private float orangeBalance = 0.0f;

  public float getBalanceFromIndex(int index) {
    List<Float> balances = List.of(appleBalance, bananaBalance, orangeBalance);
    if (index < 0 || index >= balances.size()) {
      throw new IllegalArgumentException("Invalid wallet index: " + index);
    }

    return balances.get(index);
  }
}
