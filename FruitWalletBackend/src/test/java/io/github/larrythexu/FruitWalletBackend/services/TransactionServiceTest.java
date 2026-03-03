package io.github.larrythexu.FruitWalletBackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.domain.exceptions.InsufficientFundsException;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import io.github.larrythexu.FruitWalletBackend.models.Wallet;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import io.github.larrythexu.FruitWalletBackend.repositories.TransactionRepository;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @Mock private TransactionRepository transactionRepository;
  @Mock private AccountRepository accountRepository;
  @Mock private AccountService accountService;
  @InjectMocks private TransactionService transactionService;

  @Mock Account TEST_SENDER;
  @Mock Wallet SENDER_WALLET;
  @Mock Account TEST_RECEIVER;

  private final long SENDER_ID = 1L;
  private final long RECEIVER_ID = 2L;
  private final String SENDER_NAME = "alice";
  private final String RECEIVER_NAME = "bob";
  private final Origin CURRENCY = Origin.APPLE;
  private final float AMOUNT = 10.0f;

  @Test
  void testMakeTransactionById_Success() {
    Instant timestamp = Instant.now();
    when(accountService.getAccountByID(SENDER_ID)).thenReturn(TEST_SENDER);
    when(accountService.getAccountByID(RECEIVER_ID)).thenReturn(TEST_RECEIVER);
    when(TEST_SENDER.canSend(CURRENCY, AMOUNT)).thenReturn(true);

    Transaction result =
        transactionService.makeTransactionById(SENDER_ID, RECEIVER_ID, CURRENCY, AMOUNT, timestamp);

    verify(accountService).getAccountByID(SENDER_ID);
    verify(accountService).getAccountByID(RECEIVER_ID);
    verify(accountService).subtractBalance(TEST_SENDER, CURRENCY, AMOUNT);
    verify(accountService).addBalance(TEST_RECEIVER, CURRENCY, AMOUNT);

    ArgumentCaptor<Transaction> txCaptor = ArgumentCaptor.forClass(Transaction.class);
    verify(transactionRepository).save(txCaptor.capture());
    Transaction saved = txCaptor.getValue();

    assertEquals(TEST_SENDER, saved.getSender());
    assertEquals(TEST_RECEIVER, saved.getReceiver());
    assertEquals(CURRENCY, saved.getCurrency());
    assertEquals(AMOUNT, saved.getAmount());
    assertEquals(timestamp, saved.getTimestamp());
    assertEquals(saved, result);
  }

  @Test
  void testMakeTransactionById_InsufficientFunds() {
    when(accountService.getAccountByID(SENDER_ID)).thenReturn(TEST_SENDER);
    when(accountService.getAccountByID(RECEIVER_ID)).thenReturn(TEST_RECEIVER);
    when(TEST_SENDER.canSend(CURRENCY, AMOUNT)).thenReturn(false);
    when(TEST_SENDER.getWallet()).thenReturn(SENDER_WALLET);
    when(SENDER_WALLET.getBalanceFromOrigin(CURRENCY)).thenReturn(5.0f);

    assertThrows(
        InsufficientFundsException.class,
        () ->
            transactionService.makeTransactionById(
                SENDER_ID, RECEIVER_ID, CURRENCY, AMOUNT, Instant.now()));

    verify(transactionRepository, never()).save(any());
    verify(accountService, never()).subtractBalance(any(), any(), anyFloat());
    verify(accountService, never()).addBalance(any(), any(), anyFloat());
  }

  @Test
  void testMakeTransactionByUsername_Success() {
    Instant timestamp = Instant.now();
    when(accountService.getAccountByUsername(SENDER_NAME)).thenReturn(TEST_SENDER);
    when(accountService.getAccountByUsername(RECEIVER_NAME)).thenReturn(TEST_RECEIVER);
    when(TEST_SENDER.canSend(CURRENCY, AMOUNT)).thenReturn(true);

    Transaction result =
        transactionService.makeTransactionByUsername(
            SENDER_NAME, RECEIVER_NAME, CURRENCY, AMOUNT, timestamp);

    verify(accountService).getAccountByUsername(SENDER_NAME);
    verify(accountService).getAccountByUsername(RECEIVER_NAME);
    verify(accountService).subtractBalance(TEST_SENDER, CURRENCY, AMOUNT);
    verify(accountService).addBalance(TEST_RECEIVER, CURRENCY, AMOUNT);
    verify(transactionRepository).save(result);

    assertEquals(TEST_SENDER, result.getSender());
    assertEquals(TEST_RECEIVER, result.getReceiver());
    assertEquals(CURRENCY, result.getCurrency());
    assertEquals(AMOUNT, result.getAmount());
    assertEquals(timestamp, result.getTimestamp());
  }

  @Test
  void testMakeTransactionByUsername_InsufficientFunds() {
    when(accountService.getAccountByUsername(SENDER_NAME)).thenReturn(TEST_SENDER);
    when(accountService.getAccountByUsername(RECEIVER_NAME)).thenReturn(TEST_RECEIVER);
    when(TEST_SENDER.canSend(CURRENCY, AMOUNT)).thenReturn(false);
    when(TEST_SENDER.getWallet()).thenReturn(SENDER_WALLET);
    when(SENDER_WALLET.getBalanceFromOrigin(CURRENCY)).thenReturn(5.0f);

    assertThrows(
        InsufficientFundsException.class,
        () ->
            transactionService.makeTransactionByUsername(
                SENDER_NAME, RECEIVER_NAME, CURRENCY, AMOUNT, Instant.now()));

    verify(transactionRepository, never()).save(any());
  }
}
