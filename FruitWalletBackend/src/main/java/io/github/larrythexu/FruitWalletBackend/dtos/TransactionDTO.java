package io.github.larrythexu.FruitWalletBackend.dtos;

import java.time.Instant;

public record TransactionDTO(
    String sender, String receiver, String currency, float amount, Instant timestamp) {}
