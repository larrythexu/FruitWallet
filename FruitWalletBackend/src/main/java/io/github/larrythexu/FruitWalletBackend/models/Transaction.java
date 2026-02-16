package io.github.larrythexu.FruitWalletBackend.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.Instant;

public class Transaction extends BaseEntity{

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private User receiver;

  private Double amount;
  private Instant timestamp;
}
