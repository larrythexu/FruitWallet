package io.github.larrythexu.FruitWalletBackend.dtos;

public record FactoryDTO(
    String owner,
    String origin,
    Double productionRate,
    Double currentAmount,
    Double maximumAmount) {}
