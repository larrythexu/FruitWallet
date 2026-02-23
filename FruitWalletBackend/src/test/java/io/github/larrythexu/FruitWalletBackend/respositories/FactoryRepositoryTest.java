package io.github.larrythexu.FruitWalletBackend.respositories;

import static org.junit.jupiter.api.Assertions.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import io.github.larrythexu.FruitWalletBackend.repositories.FactoryRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class FactoryRepositoryTest {

  @Autowired private FactoryRepository factoryRepository;

  @Autowired private AccountRepository accountRepository;

  private Account alice;
  private Account bob;
  private Factory aliceFactory;
  private Factory bobFactory;
  private Instant now;

  @BeforeEach
  void setUp() {
    alice = new Account("alice", Origin.APPLE);
    bob = new Account("bob", Origin.BANANA);
    accountRepository.save(alice);
    accountRepository.save(bob);

    now = Instant.now();
    aliceFactory = new Factory(alice, Origin.APPLE, 1.0f, 100.0f);
    aliceFactory.setLastClaimedAt(now);
    bobFactory = new Factory(bob, Origin.BANANA, 2.0f, 200.0f);
    bobFactory.setLastClaimedAt(now);
    aliceFactory = factoryRepository.saveAndFlush(aliceFactory);
    bobFactory = factoryRepository.saveAndFlush(bobFactory);
  }

  @Test
  public void testFindByOwner_Id() {
    List<Factory> found = factoryRepository.findByOwner_Id(alice.getId());
    assertEquals(1, found.size());
    assertEquals(alice.getId(), found.get(0).getOwner().getId());
  }

  @Test
  public void testFindByOwner_Username() {
    List<Factory> found = factoryRepository.findByOwner_Username("alice");
    assertEquals(1, found.size());
    assertEquals("alice", found.get(0).getOwner().getUsername());
  }

  @Test
  public void testFindByOwner_Username_NotExist() {
    List<Factory> found = factoryRepository.findByOwner_Username("nobody");
    assertTrue(found.isEmpty());
  }

  @Test
  public void testFindByOrigin() {
    List<Factory> found = factoryRepository.findByOrigin(Origin.APPLE);
    assertEquals(1, found.size());
    assertEquals(Origin.APPLE, found.get(0).getOrigin());
  }

  @Test
  public void testFindByProductionRate() {
    List<Factory> found = factoryRepository.findByProductionRate(1.0);
    assertEquals(1, found.size());
  }

  @Test
  public void testFindByMaximumAmount() {
    List<Factory> found = factoryRepository.findByMaximumAmount(200.0);
    assertEquals(1, found.size());
    assertEquals(bobFactory.getMaximumAmount(), found.get(0).getMaximumAmount());
  }

  @Test
  public void testFindByCreatedAt() {
    List<Factory> found = factoryRepository.findByCreatedAt(aliceFactory.getCreatedAt());
    assertFalse(found.isEmpty());
    assertTrue(found.stream().anyMatch(f -> f.getId().equals(aliceFactory.getId())));
  }

  @Test
  public void testFindByLastClaimedAt() {
    List<Factory> found = factoryRepository.findByLastClaimedAt(aliceFactory.getLastClaimedAt());
    assertFalse(found.isEmpty());
  }

  @Test
  public void testFindByCreatedAtBefore() {
    Instant future = now.plusSeconds(60);
    List<Factory> found = factoryRepository.findByCreatedAtBefore(future);
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByCreatedAtBefore_NoneMatch() {
    Instant past = now.minusSeconds(60);
    List<Factory> found = factoryRepository.findByCreatedAtBefore(past);
    assertTrue(found.isEmpty());
  }

  @Test
  public void testFindByLastClaimedAtBefore() {
    Instant future = now.plusSeconds(60);
    List<Factory> found = factoryRepository.findByLastClaimedAtBefore(future);
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByCreatedAtAfter() {
    Instant past = now.minusSeconds(60);
    List<Factory> found = factoryRepository.findByCreatedAtAfter(past);
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByCreatedAtAfter_NoneMatch() {
    Instant future = now.plusSeconds(60);
    List<Factory> found = factoryRepository.findByCreatedAtAfter(future);
    assertTrue(found.isEmpty());
  }

  @Test
  public void testFindByLastClaimedAtAfter() {
    Instant past = now.minusSeconds(360);
    var holder = aliceFactory.getLastClaimedAt();
    List<Factory> found = factoryRepository.findByLastClaimedAtAfter(past);
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByCreatedAtBetween() {
    Instant past = now.minusSeconds(60);
    Instant future = now.plusSeconds(60);
    List<Factory> found = factoryRepository.findByCreatedAtBetween(past, future);
    assertEquals(2, found.size());
  }

  @Test
  public void testFindByLastClaimedAtBetween() {
    Instant past = now.minusSeconds(60);
    Instant future = now.plusSeconds(60);
    List<Factory> found = factoryRepository.findByLastClaimedAtBetween(past, future);
    assertEquals(2, found.size());
  }
}
