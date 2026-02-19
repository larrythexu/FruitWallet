package io.github.larrythexu.FruitWalletBackend.respositories;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Wallet;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository accountRepository;

  private Account alice;
  private Account bob;

  @BeforeEach
  void setUp() {
    alice = new Account("alice", Origin.APPLE);
    bob = new Account("bob", Origin.BANANA);
    accountRepository.save(alice);
    accountRepository.save(bob);
  }

  @Test
  public void testFindByUsername() {
    Optional<Account> found = accountRepository.findByUsername(alice.getUsername());
    assertTrue(found.isPresent());
    assertEquals(alice.getUsername(), found.get().getUsername());
  }

  @Test
  public void testFindByUsername_NotExist() {
    Optional<Account> found = accountRepository.findByUsername("nobody");
    assertFalse(found.isPresent());
  }

  @Test
  public void testFindByOrigin() {
    List<Account> foundList = accountRepository.findByOrigin(Origin.BANANA);
    assertEquals(1, foundList.size());
  }
}
