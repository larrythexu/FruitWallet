package io.github.larrythexu.FruitWalletBackend.dtos;

import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.models.Transaction;
import java.util.List;

import io.github.larrythexu.FruitWalletBackend.models.Wallet;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper {
  public AccountDTO toAccountDTO(Account account) {
    List<FactoryDTO> factoryList =
        account.getFactoryList().stream().map(this::toFactoryDTO).toList();

    return new AccountDTO(
        account.getUsername(),
        account.getOrigin().toString(),
        account.getWallet().getAppleBalance(),
        account.getWallet().getBananaBalance(),
        account.getWallet().getOrangeBalance(),
        factoryList);
  }

  public FactoryDTO toFactoryDTO(Factory factory) {
    return new FactoryDTO(
        factory.getOwner().getUsername(),
        factory.getOrigin().toString(),
        factory.getProductionRate(),
        factory.getMaximumAmount());
  }

  public TransactionDTO toTransactionDTO(Transaction transaction) {
    return new TransactionDTO(
        transaction.getSender().getUsername(),
        transaction.getReceiver().getUsername(),
        transaction.getCurrency().toString(),
        transaction.getAmount(),
        transaction.getTimestamp());
  }

  public WalletDTO toWalletDTO(Wallet wallet) {
    return new WalletDTO(
        wallet.getAppleBalance(),
        wallet.getBananaBalance(),
        wallet.getOrangeBalance()
    );
  }
}
