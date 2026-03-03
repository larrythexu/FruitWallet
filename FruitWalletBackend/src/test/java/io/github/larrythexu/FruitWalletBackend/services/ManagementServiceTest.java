package io.github.larrythexu.FruitWalletBackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.dtos.ClaimDTO;
import io.github.larrythexu.FruitWalletBackend.dtos.DTOMapper;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Factory;
import io.github.larrythexu.FruitWalletBackend.models.Wallet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ManagementServiceTest {

  @Mock private AccountService accountService;
  @Mock private FactoryService factoryService;
  @Mock private DTOMapper dTOMapper;
  @InjectMocks private ManagementService managementService;

  @Test
  void testSignupAccount_CreatesAccountAndStarterFactory() {
    String username = "alice";
    Account mockAccount = mock(Account.class);
    Factory mockFactory = mock(Factory.class);

    when(mockAccount.getOrigin()).thenReturn(Origin.APPLE);
    when(accountService.createAccount(username)).thenReturn(mockAccount);
    when(factoryService.createDefaultFactory(mockAccount, Origin.APPLE)).thenReturn(mockFactory);

    Account result = managementService.signupAccount(username);

    verify(accountService).createAccount(username);
    verify(factoryService).createDefaultFactory(mockAccount, Origin.APPLE);
    verify(mockAccount).addFactory(mockFactory);
    assertEquals(mockAccount, result);
  }

  @Test
  void testComputeClaim_SingleFactory() {
    long accountId = 1L;
    Account mockAccount = mock(Account.class);
    Factory mockFactory = mock(Factory.class);
    Wallet mockWallet = mock(Wallet.class);
    ClaimDTO claimDTO = new ClaimDTO("APPLE", 5.0f);

    when(accountService.getAccountByID(accountId)).thenReturn(mockAccount);
    when(mockAccount.getFactoryList()).thenReturn(List.of(mockFactory));
    when(mockAccount.getWallet()).thenReturn(mockWallet);
    when(mockFactory.getOrigin()).thenReturn(Origin.APPLE);
    when(factoryService.claimFactoryPoints(eq(mockFactory), any())).thenReturn(claimDTO);

    List<ClaimDTO> results = managementService.computeClaim(accountId);

    verify(accountService).getAccountByID(accountId);
    verify(factoryService).claimFactoryPoints(eq(mockFactory), any());
    verify(mockWallet).addBalance(Origin.APPLE, 5.0f);

    assertEquals(1, results.size());
    assertEquals(claimDTO, results.get(0));
  }

  @Test
  void testComputeClaim_MultipleFactories() {
    long accountId = 1L;
    Account mockAccount = mock(Account.class);
    Factory appleFactory = mock(Factory.class);
    Factory bananaFactory = mock(Factory.class);
    Wallet mockWallet = mock(Wallet.class);
    ClaimDTO appleClaim = new ClaimDTO("APPLE", 3.0f);
    ClaimDTO bananaClaim = new ClaimDTO("BANANA", 7.0f);

    when(accountService.getAccountByID(accountId)).thenReturn(mockAccount);
    when(mockAccount.getFactoryList()).thenReturn(List.of(appleFactory, bananaFactory));
    when(mockAccount.getWallet()).thenReturn(mockWallet);
    when(appleFactory.getOrigin()).thenReturn(Origin.APPLE);
    when(bananaFactory.getOrigin()).thenReturn(Origin.BANANA);
    when(factoryService.claimFactoryPoints(eq(appleFactory), any())).thenReturn(appleClaim);
    when(factoryService.claimFactoryPoints(eq(bananaFactory), any())).thenReturn(bananaClaim);

    List<ClaimDTO> results = managementService.computeClaim(accountId);

    verify(mockWallet).addBalance(Origin.APPLE, 3.0f);
    verify(mockWallet).addBalance(Origin.BANANA, 7.0f);

    assertEquals(2, results.size());
    assertTrue(results.contains(appleClaim));
    assertTrue(results.contains(bananaClaim));
  }

  @Test
  void testComputeClaim_NoFactories() {
    long accountId = 1L;
    Account mockAccount = mock(Account.class);

    when(accountService.getAccountByID(accountId)).thenReturn(mockAccount);
    when(mockAccount.getFactoryList()).thenReturn(List.of());

    List<ClaimDTO> results = managementService.computeClaim(accountId);

    verify(factoryService, never()).claimFactoryPoints(any(), any());
    assertTrue(results.isEmpty());
  }
}
