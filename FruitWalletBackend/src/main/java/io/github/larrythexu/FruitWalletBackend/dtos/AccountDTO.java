package io.github.larrythexu.FruitWalletBackend.dtos;

import java.util.List;

public record AccountDTO(
    String username,
    String origin,
    float appleBalance,
    float bananaBalance,
    float orangeBalance,
    List<FactoryDTO> factoryDTOList) {}
