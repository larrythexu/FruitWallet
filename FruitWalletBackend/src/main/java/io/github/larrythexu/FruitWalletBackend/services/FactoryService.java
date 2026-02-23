package io.github.larrythexu.FruitWalletBackend.services;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.repositories.FactoryRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FactoryService {

  private final FactoryRepository factoryRepository;

  // TODO: maybe move this to factory config?
  private static final float BASIC_PRODUCTION_RATE = 1.0f;
  private static final float BASIC_MAX_AMOUNT = 100.0f;

  public FactoryService(FactoryRepository factoryRepository) {
    this.factoryRepository = factoryRepository;
  }

  public Factory createDefaultFactory(Account account, Origin origin) {
    Factory newFactory = new Factory(account, origin, BASIC_PRODUCTION_RATE, BASIC_MAX_AMOUNT);

    factoryRepository.save(newFactory);
    return newFactory;
  }

  /**
   * Claim points based on the last time claimed and rate of Factory production
   *
   * @param factory: which factory to claim from
   * @return amount of points (capped at factory max)
   */
  @Transactional
  public float claimFactoryPoints(Factory factory, Instant claimTime) {
    Instant lastClaim =
        factory.getLastClaimedAt() == null ? factory.getCreatedAt() : factory.getLastClaimedAt();

    Duration daysBetween = Duration.between(lastClaim, claimTime);
    float gainedPoints = daysBetween.toDays() * factory.getProductionRate();

    // Update factory claimed status
    factory.setLastClaimedAt(claimTime);

    return Float.min(factory.getMaximumAmount(), gainedPoints);
  }
}
