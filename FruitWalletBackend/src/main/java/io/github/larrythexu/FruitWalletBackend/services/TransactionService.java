package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.domain.exceptions.InsufficientFundsException;
import io.github.larrythexu.FruitWalletBackend.domain.exceptions.InvalidAmountException;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import io.github.larrythexu.FruitWalletBackend.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;
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

  @Transactional
  public List<Transaction> getTransactionsByUserId(long id) {
    List<Transaction> senderList = transactionRepository.findBySender_Id(id);
    List<Transaction> receiverList = transactionRepository.findByReceiver_Id(id);

    return Stream.concat(senderList.stream(), receiverList.stream()).toList();
  }

  @Transactional
  public Transaction makeTransactionById(
      long senderId, long receiverId, String currency, float amount, Instant timestamp) {
    Account sender = accountService.getAccountByID(senderId);
    Account receiver = accountService.getAccountByID(receiverId);
    Origin origin = Origin.valueOf(currency);

    if (amount <= 0.0) {
      throw new InvalidAmountException(amount);
    }

    return makeTransaction(sender, receiver, origin, amount, timestamp);
  }

  @Transactional
  public Transaction makeTransactionByUsername(
      String senderName, String receiverName, Origin currency, float amount, Instant timestamp) {
    Account sender = accountService.getAccountByUsername(senderName);
    Account receiver = accountService.getAccountByUsername(receiverName);

    return makeTransaction(sender, receiver, currency, amount, timestamp);
  }

  /**
   * Make a transaction from one account to another
   *
   * @return the transaction object representing the details of the transaction
   */
  private Transaction makeTransaction(
      Account sender, Account receiver, Origin currency, float amount, Instant timestamp) {
    // Validate transaction is possible
    if (!sender.canSend(currency, amount)) {
      float currentBalance = sender.getWallet().getBalanceFromOrigin(currency);
      throw new InsufficientFundsException(currency, amount, currentBalance);
    }

    accountService.subtractBalance(sender, currency, amount);
    accountService.addBalance(receiver, currency, amount);

    Transaction newTransaction = new Transaction(sender, receiver, currency, amount, timestamp);
    transactionRepository.save(newTransaction);
    return newTransaction;
  }
}
