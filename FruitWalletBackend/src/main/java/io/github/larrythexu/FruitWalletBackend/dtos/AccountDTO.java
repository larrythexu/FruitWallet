package io.github.larrythexu.FruitWalletBackend.dtos;

import java.util.List;

public record AccountDTO(
    String username,
    String origin,
    Double appleBalance,
    Double bananaBalance,
    Double orangeBalance,
    List<FactoryDTO> factoryDTOList) {}
