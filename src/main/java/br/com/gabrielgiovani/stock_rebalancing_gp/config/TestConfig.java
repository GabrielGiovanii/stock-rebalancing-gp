package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Wallet;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile(value = "test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private WalletService walletService;

    @Override
    public void run(String... args) throws Exception {
        Wallet w1 = new Wallet("Renda Variável",
                "Focada em ações com potencial de alta valorização e/ou pagamento de dividendos.",
                100.0);

        Wallet w2 = new Wallet("Variando Positivo",
                "Focada em ações que pagam bons dividendos.",
                100.0);

        walletService.saveAll(Arrays.asList(w1, w2));
    }
}
