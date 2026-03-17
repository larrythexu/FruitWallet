package io.github.larrythexu.FruitWalletBackend.controllers;

import io.github.larrythexu.FruitWalletBackend.dtos.DTOMapper;
import io.github.larrythexu.FruitWalletBackend.dtos.TransactionDTO;
import io.github.larrythexu.FruitWalletBackend.dtos.TransactionReq;
import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import io.github.larrythexu.FruitWalletBackend.services.TransactionService;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TransactionController {

  private final TransactionService transactionService;
  private final DTOMapper dtoMapper;

  @GetMapping("/transactions/{id}")
  public List<TransactionDTO> getTransactions(@PathVariable long id) {
    List<Transaction> transactionList = transactionService.getTransactionsByUserId(id);

    return transactionList.stream().map(dtoMapper::toTransactionDTO).toList();
  }

  @PostMapping("/transactions/make")
  public TransactionDTO makeTransaction(@Validated @RequestBody TransactionReq transactionReq) {
    Transaction transaction =
        transactionService.makeTransactionById(
            transactionReq.senderId(),
            transactionReq.receiverId(),
            transactionReq.currency(),
            transactionReq.amount(),
            Instant.now());

    return dtoMapper.toTransactionDTO(transaction);
  }
}
