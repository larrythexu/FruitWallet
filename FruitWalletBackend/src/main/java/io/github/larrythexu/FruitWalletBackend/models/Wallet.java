package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
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

  public void updateBalance(Origin origin, float amount) {
    switch (origin) {
      case APPLE -> appleBalance += amount;
      case BANANA -> bananaBalance += amount;
      case ORANGE -> orangeBalance += amount;
      default ->  throw new IllegalArgumentException("Invalid wallet origin: " + origin);
    }
  }

  public float getBalanceFromOrigin(Origin origin) {
    switch (origin) {
      case APPLE -> {
        return appleBalance;
      }
      case BANANA -> {
        return bananaBalance;
      }
      case ORANGE -> {
        return orangeBalance;
      }

      default -> {
        return 0;
      }
    }
  }
}
