package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CategoryService;
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
    private PortfolioService portfolioService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        Portfolio p1 = new Portfolio("Renda Variável",
                "Focada em ações com potencial de alta valorização e/ou pagamento de dividendos.", 100.0);
        Portfolio p2 = new Portfolio("Variando Positivo",
                "Focada em ações que pagam bons dividendos.", 100.0);
        portfolioService.saveAll(Arrays.asList(p1, p2));

        Category c1 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 60.0);
        Category c2 = new Category("Small Cap",
                "Empresas pequenas com grande potencial de valorização.", 40.0);
        categoryService.saveAll(Arrays.asList(c1, c2));
    }
}
