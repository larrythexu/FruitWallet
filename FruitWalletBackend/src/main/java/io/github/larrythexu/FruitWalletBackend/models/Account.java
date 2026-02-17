package io.github.larrythexu.FruitWalletBackend.models;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import jakarta.persistence.*;
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

  private String username;

  @Enumerated(EnumType.STRING)
  private Origin origin;

  // Use this to calculate our increasing balance
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Factory> factoryList;

  // Balances - todo: shrink this into one class?
  private Double appleBalance;
  private Double bananaBalance;
  private Double orangeBalance;

  @OneToMany(mappedBy = "sender")
  private List<Transaction> sentTransactions;

  @OneToMany(mappedBy = "receiver")
  private List<Transaction> receivedTransactions;

  public void addFactory(Factory factory) {
    factoryList.add(factory);
  }

  // GOOGLE OAUTH STUFF for later?
  //  @Column(unique = true)
  //  private String googleId;
  //
  //  private String name;
  //  private String email
}
