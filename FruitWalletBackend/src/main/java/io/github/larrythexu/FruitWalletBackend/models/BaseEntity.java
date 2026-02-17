package io.github.larrythexu.FruitWalletBackend.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity {
  @Id @GeneratedValue private Long id;

  @CreationTimestamp private Instant createdAt;
}
