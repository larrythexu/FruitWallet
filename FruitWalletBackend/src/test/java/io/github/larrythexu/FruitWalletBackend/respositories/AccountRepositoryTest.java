package io.github.larrythexu.FruitWalletBackend.respositories;

import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void testFindByUsername() {
    Account alice = Account.builder().username("alice").build();
    entityManager.persist(alice);
    entityManager.flush();

    Optional<Account> found = accountRepository.findByUsername(alice.getUsername());
    assertTrue(found.isPresent());
    assertEquals(alice.getUsername(), found.get().getUsername());
  }
}
