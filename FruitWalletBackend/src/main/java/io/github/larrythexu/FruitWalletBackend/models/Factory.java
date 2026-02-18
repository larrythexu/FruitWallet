package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Factory extends BaseEntity {

  private Origin origin;
  private float productionRate; // productionRate PER day

  //  private float currentAmount;
  private float maximumAmount;

  // Use UTC time
  private Instant lastClaimedAt;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Account owner;

  public Factory(Account owner, Origin origin, float productionRate, float maximumAmount) {
    this.setOwner(owner);
    this.setOrigin(origin);
    this.setProductionRate(productionRate);
    this.setMaximumAmount(maximumAmount);

    this.setLastClaimedAt(this.getCreatedAt());
  }
}
