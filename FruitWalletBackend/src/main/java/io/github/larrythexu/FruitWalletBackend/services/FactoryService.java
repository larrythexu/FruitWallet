package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.repositories.FactoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FactoryService {

  private final FactoryRepository factoryRepository;

  // TODO: maybe move this to factory config?
  private static final double BASIC_PRODUCTION_RATE = 1.0;
  private static final double BASIC_START_AMOUNT = 0.0;
  private static final double BASIC_MAX_AMOUNT = 100.0;

  public FactoryService(FactoryRepository factoryRepository) {
    this.factoryRepository = factoryRepository;
  }

  public Factory createDefaultFactory(Origin origin) {
    Factory newFactory =
        Factory.builder()
            .origin(origin)
            .productionRate(BASIC_PRODUCTION_RATE)
            .currentAmount(BASIC_START_AMOUNT)
            .maximumAmount(BASIC_MAX_AMOUNT)
            .build();

    factoryRepository.save(newFactory);
    return newFactory;
  }
}
