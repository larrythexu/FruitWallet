package io.github.larrythexu.FruitWalletBackend.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Origin {
  APPLE(0),
  BANANA(1),
  ORANGE(2);
  // STRAWBERRY
  //  PEACH,
  //  WATERMELON

  private final int index;

  Origin(int index) {
    this.index = index;
  }

  public static Optional<Origin> ofIndex(int index) {
    return Arrays.stream(values()).filter(origin -> origin.index == index).findFirst();
  }
}
