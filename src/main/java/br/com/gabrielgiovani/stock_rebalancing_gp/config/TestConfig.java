package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile(value = "test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private PortfolioService walletService;

    @Override
    public void run(String... args) throws Exception {
        Portfolio p1 = new Portfolio("Renda Variável",
                "Focada em ações com potencial de alta valorização e/ou pagamento de dividendos.",
                100.0);

        Portfolio p2 = new Portfolio("Variando Positivo",
                "Focada em ações que pagam bons dividendos.",
                100.0);

        walletService.saveAll(Arrays.asList(p1, p2));
    }
}
