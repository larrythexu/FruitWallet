package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

public class Factory extends BaseEntity {

  private Origin origin;
  private Double productionRate;

  private Double currentAmount;
  private Double maximumAmount;

  // Use UTC time
  private Instant createdAt;
  private Instant lastClaimedAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
