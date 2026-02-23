package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.domain.exceptions.InsufficientFundsException;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import io.github.larrythexu.FruitWalletBackend.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final AccountService accountService;

  public TransactionService(
      TransactionRepository transactionRepository,
      AccountRepository accountRepository,
      AccountService accountService) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.accountService = accountService;
  }

  /**
   * Make a transaction from one account to another
   *
   * @return the transaction object representing the details of the transaction
   */
  @Transactional
  public Transaction makeTransaction(
      Account sender, Account receiver, Origin currency, float amount, Instant timestamp) {
    // Validate transaction is possible
    if (!sender.canSend(currency, amount)) {
      throw new InsufficientFundsException(sender, currency, amount);
    }

    Transaction newTransaction = new Transaction(sender, receiver, currency, amount, timestamp);
    transactionRepository.save(newTransaction);
    return newTransaction;
  }
}
