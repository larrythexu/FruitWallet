package io.github.larrythexu.FruitWalletBackend;

import com.github.javafaker.Faker;
import io.github.larrythexu.FruitWalletBackend.domain.enums.Origin;
import io.github.larrythexu.FruitWalletBackend.models.Account;
import io.github.larrythexu.FruitWalletBackend.models.Wallet;
import io.github.larrythexu.FruitWalletBackend.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FruitWalletBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(FruitWalletBackendApplication.class, args);
  }

//  	@Bean
//  	public CommandLineRunner commandLineRunner(
//  			AccountRepository accountRepository
//  	) {
//  		return args -> {
//
//  			for (int i = 0; i < 25; i++) {
//  				Faker faker = new Faker();
//  				Origin tempOrigin;
//  				if (i % 3 == 0) {
//  					tempOrigin = Origin.APPLE;
//  				} else if (i % 3 == 1) {
//  					tempOrigin = Origin.ORANGE;
//  				} else {
//  					tempOrigin = Origin.BANANA;
//  				}
//
//          var wallet = new Wallet();
//          wallet.setAppleBalance((float) faker.number().randomDouble(2, 0, 100));
//          wallet.setBananaBalance((float) faker.number().randomDouble(2, 0, 100));
//          wallet.setOrangeBalance((float) faker.number().randomDouble(2, 0, 100));
//
//  				var account = Account.builder()
//  						.username(faker.name().username())
//  						.origin(tempOrigin)
//  						.wallet(wallet)
//  						.build();
//
//  				accountRepository.save(account);
//  			}
//  		};
//  	}
}
