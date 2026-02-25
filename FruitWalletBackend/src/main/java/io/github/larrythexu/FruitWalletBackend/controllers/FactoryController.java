package io.github.larrythexu.FruitWalletBackend.controllers;

import io.github.larrythexu.FruitWalletBackend.dtos.DTOMapper;
import io.github.larrythexu.FruitWalletBackend.dtos.FactoryDTO;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.services.FactoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class FactoryController {

  private final FactoryService factoryService;
  private final DTOMapper dtoMapper;

  @GetMapping("/factory/{id}")
  public FactoryDTO getFactory(@PathVariable long id) {
    Factory factory = factoryService.getFactoryById(id);

    return dtoMapper.toFactoryDTO(factory);
  }
}
