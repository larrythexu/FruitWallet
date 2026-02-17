package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Factory extends BaseEntity {

  private Origin origin;
  private Double productionRate;

  private Double currentAmount;
  private Double maximumAmount;

  // Use UTC time
  private Instant createdAt;
  private Instant lastClaimedAt;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
}
