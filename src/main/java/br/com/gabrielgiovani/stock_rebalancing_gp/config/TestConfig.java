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
import java.util.stream.Stream;

@Configuration
@Profile(value = "test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private TestService testService;

    private static final Map<String, UserTest> userTestMap = new HashMap<>();


    @Override
    public void run(String... args) throws Exception {
        User u1 = new User("gabriel", "1234", "Gabriel Giovani");
        User u2 = new User("test2", "4321", "Teste Legal");
        User u3 = new User("test3", "4444", "Test Test");
        User u4 = new User("test4", "555", "Test TG");
        testService.saveAllUsers(Arrays.asList(u1, u2, u3, u4));

        Stream.of(u1, u2, u3, u4).map(UserTest::new).forEach(obj -> userTestMap.put(obj.getUsername(), obj));
        UserTest ut1 = userTestMap.get("gabriel");
        UserTest ut2 = userTestMap.get("test2");
        UserTest ut3 = userTestMap.get("test3");
        UserTest ut4 = userTestMap.get("test4");

        Portfolio p1 = new Portfolio("Renda Variável",
                "Focada em ações com potencial de alta valorização e/ou pagamento de dividendos.",
                100.0,
                u1);
        Portfolio p2 = new Portfolio("Variando Positivo",
                "Focada em ações que pagam bons dividendos.", 100.0, u2);
        Portfolio p3 = new Portfolio("Bolsa",
                "Focada em variar.", 100.0, u3);
        Portfolio p4 = new Portfolio("Variando Positivo2",
                "Focada em ações que pagam bons dividendos.", 100.0, u4);

        u1.setPortfolio(p1);
        u2.setPortfolio(p2);
        u3.setPortfolio(p3);
        u4.setPortfolio(p4);
        testService.saveAllPortfolios(Arrays.asList(p1, p2, p3, p4));

        ut1.getPortfolioMap().add(p1);
        ut2.getPortfolioMap().add(p2);
        ut3.getPortfolioMap().add(p3);
        ut4.getPortfolioMap().add(p4);

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
        Category c6 = new Category("Small Cap",
                "Empresas pequenas com grande potencial de valorização.", 40.0, p3);

        p1.getCategories().add(c1);
        p1.getCategories().add(c2);
        p1.getCategories().add(c3);
        p2.getCategories().add(c4);
        p3.getCategories().add(c5);
        p3.getCategories().add(c6);
        testService.saveAllCategories(Arrays.asList(c1, c2, c3, c4, c5, c6));

        ut1.getCategoryMap().add(c1);
        ut1.getCategoryMap().add(c2);
        ut1.getCategoryMap().add(c3);
        ut2.getCategoryMap().add(c4);
        ut3.getCategoryMap().add(c5);

        Sector s1 = new Sector("Bancos", "Banqueiros", SectorNature.NON_CYCLICAL);
        Sector s2 = new Sector("Holding", "Misto de empresas", SectorNature.CYCLICAL);
        Sector s3 = new Sector("Construção Civíl", "Misto de projetos", SectorNature.CYCLICAL);
        Sector s4 = new Sector("Varejo", "Quebradeira é certa", SectorNature.CYCLICAL);
        Sector s5 = new Sector("Varejo", "Quebradeira é certa", SectorNature.CYCLICAL);

        CategorySector cs1 = new CategorySector(c1, s1, 10.0);
        CategorySector cs2 = new CategorySector(c1, s2, 5.0);
        CategorySector cs3 = new CategorySector(c1, s3, 8.0);
        CategorySector cs4 = new CategorySector(c4, s4, 6.0);
        CategorySector cs5 = new CategorySector(c5, s5, 6.5);

        s1.getCategoriesSector().add(cs1);
        s2.getCategoriesSector().add(cs2);
        s3.getCategoriesSector().add(cs3);
        s4.getCategoriesSector().add(cs4);
        s5.getCategoriesSector().add(cs5);
        c1.getCategorySectors().add(cs1);
        c1.getCategorySectors().add(cs2);
        c1.getCategorySectors().add(cs3);
        c4.getCategorySectors().add(cs4);
        c5.getCategorySectors().add(cs5);

        testService.saveAllSector(Arrays.asList(s1, s2, s3, s4, s5));
        testService.saveAllCategorySector(Arrays.asList(cs1, cs2, cs3, cs4, cs5));

        ut1.getSectorMap().add(s1);
        ut1.getSectorMap().add(s2);
        ut1.getSectorMap().add(s3);
        ut2.getSectorMap().add(s4);
        ut3.getSectorMap().add(s5);

        ut1.getCategorySectorMap().add(cs1);
        ut1.getCategorySectorMap().add(cs2);
        ut1.getCategorySectorMap().add(cs3);
        ut2.getCategorySectorMap().add(cs4);
        ut3.getCategorySectorMap().add(cs5);
    }

    public static Map<String, UserTest> getUserTestMap() {
        return userTestMap;
    }
}