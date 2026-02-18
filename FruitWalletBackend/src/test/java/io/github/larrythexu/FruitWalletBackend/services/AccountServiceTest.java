package io.github.larrythexu.FruitWalletBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  @Mock private AccountRepository accountRepository;
  @InjectMocks private AccountService accountService;

  private final String TEST_USERNAME_1 = "TEST";
  private final String TEST_USERNAME_2 = "TEST_2";
  private final float DEFAULT_BALANCE = 0.0f;

  @Test
  void testCreateAccount() {
    when(accountRepository.findByUsername(TEST_USERNAME_1)).thenReturn(Optional.empty());
    when(accountRepository.findByUsername(TEST_USERNAME_2)).thenReturn(Optional.empty());

    Account newAccount = accountService.createAccount(TEST_USERNAME_1);

    verify(accountRepository).findByUsername(TEST_USERNAME_1);
    verify(accountRepository).save(newAccount);
    assertEquals(TEST_USERNAME_1, newAccount.getUsername());
    assertEquals(Origin.APPLE, newAccount.getOrigin());
    assertEquals(DEFAULT_BALANCE, newAccount.getWallet().getAppleBalance());
    assertEquals(List.of(), newAccount.getFactoryList());
    assertEquals(List.of(), newAccount.getSentTransactions());

    // Testing second user creation
    Account newAccount2 = accountService.createAccount(TEST_USERNAME_2);
    assertEquals(TEST_USERNAME_2, newAccount2.getUsername());
    assertEquals(Origin.BANANA, newAccount2.getOrigin());
  }

  @Test
  void testExistingAccount() {
    Account existingAccount = Account.builder().username(TEST_USERNAME_1).build();
    when(accountRepository.findByUsername(TEST_USERNAME_1))
        .thenReturn(Optional.of(existingAccount));

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          accountService.createAccount(TEST_USERNAME_1);
        });
  }
}
