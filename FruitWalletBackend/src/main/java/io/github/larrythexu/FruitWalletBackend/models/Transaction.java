package io.github.larrythexu.FruitWalletBackend.models;

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
public class Transaction extends BaseEntity{

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Account sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private Account receiver;

  private Double amount;
  private Instant timestamp;
}
