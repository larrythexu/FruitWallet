package io.github.larrythexu.FruitWalletBackend.repositories;

import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findBySender_Id(Long sender_id);
  List<Transaction> findByReceiver_Id(Long receiver_id);
  List<Transaction> findBySender_Username(String username);
  List<Transaction> findByReceiver_Username(String username);

  @Query("SELECT t FROM Transaction t WHERE t.receiver.username = :username OR t.sender.username = :username")
  List<Transaction> findByUsername(String username);

  List<Transaction> findByAmount(Double amount);


}
