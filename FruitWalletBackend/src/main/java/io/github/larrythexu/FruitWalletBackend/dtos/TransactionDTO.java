package io.github.larrythexu.FruitWalletBackend.dtos;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import java.time.LocalDateTime;

public record TransactionDTO(
    String sender, String receiver, Origin currency, Double amount, LocalDateTime timestamp) {}
