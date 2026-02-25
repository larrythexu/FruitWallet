package io.github.larrythexu.FruitWalletBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.dtos.ClaimDTO;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.repositories.FactoryRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class FactoryServiceTest {

  @Mock private FactoryRepository factoryRepository;
  @InjectMocks private FactoryService factoryService;

  @Mock Account TEST_ACCOUNT;
  @Mock Factory TEST_FACTORY;

  @Test
  void testCreateDefaultFactory() {
    Factory newFactory = factoryService.createDefaultFactory(TEST_ACCOUNT, Origin.ORANGE);

    verify(factoryRepository).save(newFactory);
    assertEquals(1.0f, newFactory.getProductionRate());
    assertEquals(100.0f, newFactory.getMaximumAmount());
    assertEquals(newFactory.getCreatedAt(), newFactory.getLastClaimedAt());
  }

  @Test
  void testClaimPoints() {
    Instant claimTime = Instant.now();
    // Just after 2 days, we should get 2 points
    Instant lastClaimedTime = Instant.now().minus(49, ChronoUnit.HOURS);
    when(TEST_FACTORY.getLastClaimedAt()).thenReturn(lastClaimedTime);
    when(TEST_FACTORY.getProductionRate()).thenReturn(1.0f);
    when(TEST_FACTORY.getMaximumAmount()).thenReturn(100.0f);
    when(TEST_FACTORY.getOrigin()).thenReturn(Origin.APPLE);

    ClaimDTO claimed = factoryService.claimFactoryPoints(TEST_FACTORY, claimTime);

    verify(TEST_FACTORY).setLastClaimedAt(claimTime);
    assertEquals(2.0f, claimed.balance());
  }

  @Test
  void testMaxPoints() {
    Instant claimTime = Instant.now();
    // Just after 2 days, we should hit the cap of 15.0f
    Instant lastClaimedTime = Instant.now().minus(49, ChronoUnit.HOURS);
    when(TEST_FACTORY.getLastClaimedAt()).thenReturn(lastClaimedTime);
    when(TEST_FACTORY.getProductionRate()).thenReturn(10.0f);
    when(TEST_FACTORY.getMaximumAmount()).thenReturn(15.0f);
    when(TEST_FACTORY.getOrigin()).thenReturn(Origin.APPLE);

    ClaimDTO claimed = factoryService.claimFactoryPoints(TEST_FACTORY, claimTime);

    verify(TEST_FACTORY).setLastClaimedAt(claimTime);
    assertEquals(15.0f, claimed.balance());
  }
}
