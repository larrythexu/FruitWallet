package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Account extends BaseEntity {

  private String username;

  @Enumerated(EnumType.STRING)
  private Origin origin;

  // Use this to calculate our increasing balance
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
//  @OneToMany(mappedBy = "account")
  private List<Factory> factoryList;

  // Balances
  private Double appleBalance;
  private Double bananaBalance;
  private Double orangeBalance;

  @OneToMany(mappedBy = "sender")
  private List<Transaction> sentTransactions;
  @OneToMany(mappedBy = "receiver")
  private List<Transaction> receivedTransactions;

  // GOOGLE OAUTH STUFF for later?
//  @Column(unique = true)
//  private String googleId;
//
//  private String name;
//  private String email
}
