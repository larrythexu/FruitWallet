package io.github.larrythexu.FruitWalletBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.repositories.FactoryRepository;
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

  @Test
  void testCreateDefaultFactory() {
    Factory newFactory = factoryService.createDefaultFactory(Origin.ORANGE);

    verify(factoryRepository).save(newFactory);
    assertEquals(1.0, newFactory.getProductionRate());
    assertEquals(0.0, newFactory.getCurrentAmount());
    assertEquals(100.0, newFactory.getMaximumAmount());
  }
}
