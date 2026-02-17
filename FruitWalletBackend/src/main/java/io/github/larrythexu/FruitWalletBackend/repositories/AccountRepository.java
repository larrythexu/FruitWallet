package io.github.larrythexu.FruitWalletBackend.repositories;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByUsername(String username);
  List<Account> findByOrigin(Origin origin);

  @Query(
      """
      SELECT a FROM Account a JOIN Transaction t
      ON a.id = t.sender.id OR a.id = t.receiver.id
      WHERE t.id = :tx_id
      """
  )
  List<Account> findAccountsByTransactionId(Long tx_id);

//  GOOGLE OAUTH IMPLEMENTATION METHODS
//  User findByEmailAddress(String email);
}
