package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.*;
import br.com.gabrielgiovani.stock_rebalancing_gp.enums.SectorNature;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile(value = "test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private TestService testService;

    private static final Map<String, User> userMap = new HashMap<>();
    private static final Map<String, Portfolio> portfolioMap = new HashMap<>();
    private static final Map<String, Category> categoryMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User("gabriel", "1234", "Gabriel Giovani");
        User u2 = new User("teste", "4321", "Teste Legal");
        User u3 = new User("test", "4444", "Test Test");
        User u4 = new User("test123", "555", "Test TG");
        Arrays.asList(u1, u2, u3, u4).forEach(obj -> userMap.put(obj.getUsername(), obj));
        testService.saveAllUsers(Arrays.asList(u1, u2, u3, u4));

        Portfolio p1 = new Portfolio("Renda Variável",
                "Focada em ações com potencial de alta valorização e/ou pagamento de dividendos.",
                100.0,
                u1);
        Portfolio p2 = new Portfolio("Variando Positivo",
                "Focada em ações que pagam bons dividendos.", 100.0, u2);
        Portfolio p3 = new Portfolio("Bolsa",
                "Focada em variar.", 100.0, u3);

        u1.setPortfolio(p1);
        u2.setPortfolio(p2);
        u3.setPortfolio(p3);
        Arrays.asList(p1, p2, p3).forEach(obj -> portfolioMap.put(obj.getName(), obj));
        testService.saveAllPortfolios(Arrays.asList(p1, p2, p3));

        Category c1 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 60.0, p1);
        Category c2 = new Category("Small Cap",
                "Empresas pequenas com grande potencial de valorização.", 40.0, p1);
        Category c3 = new Category("Mid Cap",
                "Empresas médias com médio potencial de valorização e que pagam dividendos.",
                40.0, p1);
        Category c4 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 60.0, p2);
        Category c5 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 60.0, p3);

        p1.getCategories().add(c1);
        p1.getCategories().add(c2);
        p1.getCategories().add(c3);
        p2.getCategories().add(c4);
        p3.getCategories().add(c5);
        Arrays.asList(c1, c2, c3, c4).forEach(obj -> categoryMap.put(obj.getName(), obj));
        testService.saveAllCategories(Arrays.asList(c1, c2, c3, c4, c5));

        Sector s1 = new Sector("Bancos", "Banqueiros", SectorNature.NON_CYCLICAL);
        Sector s2 = new Sector("Holding", "Misto de empresas", SectorNature.CYCLICAL);
        Sector s3 = new Sector("Construção Civíl", "Misto de projetos", SectorNature.CYCLICAL);
        Sector s4 = new Sector("Varejo", "Quebradeira é certa", SectorNature.CYCLICAL);

        CategorySector cs1 = new CategorySector(c1, s1, 10.0);
        CategorySector cs2 = new CategorySector(c1, s2, 5.0);
        CategorySector cs3 = new CategorySector(c1, s3, 8.0);
        CategorySector cs4 = new CategorySector(c4, s4, 6.0);

        s1.getCategoriesSector().add(cs1);
        s2.getCategoriesSector().add(cs2);
        s3.getCategoriesSector().add(cs3);
        s4.getCategoriesSector().add(cs4);
        c1.getCategorySectors().add(cs1);
        c1.getCategorySectors().add(cs2);
        c1.getCategorySectors().add(cs3);
        c4.getCategorySectors().add(cs4);

        testService.saveAllSector(Arrays.asList(s1, s2, s3, s4));
        testService.saveAllCategorySector(Arrays.asList(cs1, cs2, cs3, cs4));
    }

    public static Map<String, User> getUserMap() {
        return userMap;
    }

    public static Map<String, Portfolio> getPortfolioMap() {
        return portfolioMap;
    }

    public static Map<String, Category> getCategoryMap() {
        return categoryMap;
    }
}