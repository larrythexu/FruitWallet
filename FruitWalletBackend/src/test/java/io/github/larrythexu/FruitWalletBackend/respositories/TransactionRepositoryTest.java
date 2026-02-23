package io.github.larrythexu.FruitWalletBackend.respositories;

import static org.junit.jupiter.api.Assertions.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import io.github.larrythexu.FruitWalletBackend.repositories.TransactionRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class TransactionRepositoryTest {

  @Autowired private TransactionRepository transactionRepository;

  @Autowired private AccountRepository accountRepository;

  private Account alice;
  private Account bob;
  private Account carol;
  private Transaction aliceToBob;
  private Transaction bobToAlice;

  @BeforeEach
  void setUp() {
    alice = new Account("alice", Origin.APPLE);
    bob = new Account("bob", Origin.BANANA);
    carol = new Account("carol", Origin.APPLE);
    accountRepository.save(alice);
    accountRepository.save(bob);
    accountRepository.save(carol);

    aliceToBob = new Transaction();
    aliceToBob.setSender(alice);
    aliceToBob.setReceiver(bob);
    aliceToBob.setCurrency(Origin.APPLE);
    aliceToBob.setAmount(10.0f);
    aliceToBob.setTimestamp(Instant.now());

    bobToAlice = new Transaction();
    bobToAlice.setSender(bob);
    bobToAlice.setReceiver(alice);
    bobToAlice.setCurrency(Origin.BANANA);
    bobToAlice.setAmount(5.0f);
    bobToAlice.setTimestamp(Instant.now());

    transactionRepository.save(aliceToBob);
    transactionRepository.save(bobToAlice);
  }

  @Test
  public void testFindBySender_Id() {
    List<Transaction> found = transactionRepository.findBySender_Id(alice.getId());
    assertEquals(1, found.size());
    assertEquals(alice.getId(), found.get(0).getSender().getId());
  }

  @Test
  public void testFindBySender_Id_NotExist() {
    List<Transaction> found = transactionRepository.findBySender_Id(carol.getId());
    assertTrue(found.isEmpty());
  }

  @Test
  public void testFindByReceiver_Id() {
    List<Transaction> found = transactionRepository.findByReceiver_Id(bob.getId());
    assertEquals(1, found.size());
    assertEquals(bob.getId(), found.get(0).getReceiver().getId());
  }

  @Test
  public void testFindBySender_Username() {
    List<Transaction> found = transactionRepository.findBySender_Username("bob");
    assertEquals(1, found.size());
    assertEquals("bob", found.get(0).getSender().getUsername());
  }

  @Test
  public void testFindByReceiver_Username() {
    List<Transaction> found = transactionRepository.findByReceiver_Username("alice");
    assertEquals(1, found.size());
    assertEquals("alice", found.get(0).getReceiver().getUsername());
  }

  @Test
  public void testFindByUsername_AsSender() {
    List<Transaction> found = transactionRepository.findByUsername("alice");
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByUsername_AsReceiver() {
    List<Transaction> found = transactionRepository.findByUsername("bob");
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByUsername_NotInvolved() {
    List<Transaction> found = transactionRepository.findByUsername("carol");
    assertTrue(found.isEmpty());
  }

  @Test
  public void testFindByAmount() {
    List<Transaction> found = transactionRepository.findByAmount(10.0);
    assertEquals(1, found.size());
    assertEquals(aliceToBob.getId(), found.get(0).getId());
  }

  @Test
  public void testFindByAmount_NotExist() {
    List<Transaction> found = transactionRepository.findByAmount(999.0);
    assertTrue(found.isEmpty());
  }
}
