package io.github.larrythexu.FruitWalletBackend.dtos;

public record TransactionReq(long senderId, long receiverId, String currency, float amount) {}
