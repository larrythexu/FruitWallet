package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Getter
@Setter
public class Account extends BaseEntity {

  private static final float START_BALANCE = 0.0f;

  private String username;

  @Enumerated(EnumType.STRING)
  private Origin origin;

  // Use this to calculate our increasing balance
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Factory> factoryList;

  @Embedded private Wallet wallet;

  @OneToMany(mappedBy = "sender")
  private List<Transaction> sentTransactions;

  @OneToMany(mappedBy = "receiver")
  private List<Transaction> receivedTransactions;

  public Account(String username, Origin origin) {
    this.username = username;
    this.origin = origin;
    this.factoryList = new ArrayList<>();
    this.wallet = new Wallet();
    this.sentTransactions = new ArrayList<>();
    this.receivedTransactions = new ArrayList<>();
  }

  public void addFactory(Factory factory) {
    factoryList.add(factory);
    factory.setOwner(this);
  }

  public boolean canSend(Origin currency, float transactionAmount) {
    return wallet.getBalanceFromIndex(currency.ordinal()) >= transactionAmount;
  }
  // GOOGLE OAUTH STUFF for later?
  //  @Column(unique = true)
  //  private String googleId;
  //
  //  private String name;
  //  private String email
}
