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
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Transaction extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Account sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private Account receiver;

  private Origin currency; // which currency
  private Double amount;
  private Instant timestamp;

  //  TODO: converting instant to localtime?
  // LocalTime.ofInstant(timestamp, ZoneId.systemDefault())
}
