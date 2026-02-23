package io.github.larrythexu.FruitWalletBackend.repositories;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepository extends JpaRepository<Factory, Long> {

  List<Factory> findByOwner_Id(Long id);

  List<Factory> findByOwner_Username(String username);

  List<Factory> findByOrigin(Origin origin);

  List<Factory> findByProductionRate(Double productionRate);

  List<Factory> findByMaximumAmount(Double maximumAmount);

  List<Factory> findByCreatedAt(Instant date);

  List<Factory> findByLastClaimedAt(Instant date);

  List<Factory> findByCreatedAtBefore(Instant date);

  List<Factory> findByLastClaimedAtBefore(Instant date);

  List<Factory> findByCreatedAtAfter(Instant date);

  List<Factory> findByLastClaimedAtAfter(Instant date);

  List<Factory> findByCreatedAtBetween(Instant startDate, Instant endDate);

  List<Factory> findByLastClaimedAtBetween(Instant startDate, Instant endDate);
}
