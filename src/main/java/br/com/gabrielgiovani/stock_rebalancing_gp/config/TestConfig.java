package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.*;
import br.com.gabrielgiovani.stock_rebalancing_gp.enums.SectorNature;
import br.com.gabrielgiovani.stock_rebalancing_gp.enums.StockType;
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

        ut1.getPortfolioSet().add(p1);
        ut2.getPortfolioSet().add(p2);
        ut3.getPortfolioSet().add(p3);
        ut4.getPortfolioSet().add(p4);

        Category c1 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 40.0, p1);
        Category c2 = new Category("Small Cap",
                "Empresas pequenas com grande potencial de valorização.", 30.0, p1);
        Category c3 = new Category("Mid Cap",
                "Empresas médias com médio potencial de valorização e que pagam dividendos.",
                40.0, p1);
        Category c4 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 30.0, p2);
        Category c5 = new Category("Total Return",
                "Empresas sólidas no mercado que pagam dividendos.", 60.0, p3);
        Category c6 = new Category("Small Cap",
                "Empresas pequenas com grande potencial de valorização.", 20.0, p3);

        p1.getCategories().add(c1);
        p1.getCategories().add(c2);
        p1.getCategories().add(c3);
        p2.getCategories().add(c4);
        p3.getCategories().add(c5);
        p3.getCategories().add(c6);
        testService.saveAllCategories(Arrays.asList(c1, c2, c3, c4, c5, c6));

        ut1.getCategorySet().add(c1);
        ut1.getCategorySet().add(c2);
        ut1.getCategorySet().add(c3);
        ut2.getCategorySet().add(c4);
        ut3.getCategorySet().add(c5);
        ut3.getCategorySet().add(c6);

        Sector s1 = new Sector("Banco", null, SectorNature.CYCLICAL);
        Sector s2 = new Sector("Energia Elétrica", null, SectorNature.NON_CYCLICAL);
        Sector s3 = new Sector("Agricultura", null, SectorNature.NON_CYCLICAL);
        Sector s4 = new Sector("Incorporações",
                "Atividades voltadas para administração de shopping center e edifícios empresariais.",
                SectorNature.CYCLICAL);
        Sector s5 = new Sector("Construção Civíl", null, SectorNature.CYCLICAL);
        Sector s6 = new Sector("Construção Civíl", null, SectorNature.CYCLICAL);

        CategorySector cs1 = new CategorySector(c1, s1, 10.0);
        CategorySector cs2 = new CategorySector(c1, s2, 5.0);
        CategorySector cs3 = new CategorySector(c1, s3, 8.0);
        CategorySector cs4 = new CategorySector(c4, s4, 6.0);
        CategorySector cs5 = new CategorySector(c5, s5, 6.5);
        CategorySector cs6 = new CategorySector(c5, s6, 6.5);

        s1.getCategoriesSector().add(cs1);
        s2.getCategoriesSector().add(cs2);
        s3.getCategoriesSector().add(cs3);
        s4.getCategoriesSector().add(cs4);
        s5.getCategoriesSector().add(cs5);
        s6.getCategoriesSector().add(cs6);
        c1.getCategorySectors().add(cs1);
        c1.getCategorySectors().add(cs2);
        c1.getCategorySectors().add(cs3);
        c4.getCategorySectors().add(cs4);
        c5.getCategorySectors().add(cs5);
        c5.getCategorySectors().add(cs6);
        testService.saveAllSectors(Arrays.asList(s1, s2, s3, s4, s5, s6));
        testService.saveAllCategorySectors(Arrays.asList(cs1, cs2, cs3, cs4, cs5, cs6));

        ut1.getSectorSet().add(s1);
        ut1.getSectorSet().add(s2);
        ut1.getSectorSet().add(s3);
        ut2.getSectorSet().add(s4);
        ut3.getSectorSet().add(s5);
        ut3.getSectorSet().add(s6);

        ut1.getCategorySectorSet().add(cs1);
        ut1.getCategorySectorSet().add(cs2);
        ut1.getCategorySectorSet().add(cs3);
        ut2.getCategorySectorSet().add(cs4);
        ut3.getCategorySectorSet().add(cs5);
        ut3.getCategorySectorSet().add(cs6);

        Company co1 = new Company(
                "BCO PAN S.A.",
                null,
                "59285411000113",
                "BTG PACTUAL SERVIÇOS FINANCEIROS SA DTVM BTG PACTUAL SERVIÇOS FINANCEIROS SA DTVM",
                s1);
        Company co2 = new Company(
                "ITAU UNIBANCO HOLDING S.A.",
                null,
                "60872504000123",
                "ITAU CORRETORA ACOES ITAU CORRETORA ACOES",
                s1);
        Company co3 = new Company(
                "CIA ENERGETICA DE MINAS GERAIS - CEMIG",
                null,
                "17155730000164",
                "ITAU CORRETORA ACOES ITAU CORRETORA ACOES",
                s2);
        Company co4 = new Company(
                "NEOENERGIA S.A.",
                null,
                "01083200000118",
                "ITAU CORRETORA ACOES",
                s2);
        Company co5 = new Company(
                "BRASILAGRO - CIA BRAS DE PROP AGRICOLAS",
                null,
                "07628528000159",
                "ITAU CORRETORA ACOES",
                s3);
        Company co6 = new Company(
                "JHSF PARTICIPACOES S.A.",
                null,
                "08294224000165",
                "BTG PACTUAL SERVIÇOS FINANCEIROS SA DTVM",
                s4);
        Company co7 = new Company(
                "CURY CONSTRUTORA E INCORPORADORA S.A.",
                null,
                "08797760000183",
                "ITAU CORRETORA ACOES",
                s5);

        co1.setSector(s1);
        co2.setSector(s1);
        co3.setSector(s2);
        co4.setSector(s2);
        co5.setSector(s3);
        co6.setSector(s4);
        co7.setSector(s5);
        s1.getCompanies().add(co1);
        s1.getCompanies().add(co2);
        s2.getCompanies().add(co3);
        s2.getCompanies().add(co4);
        s3.getCompanies().add(co5);
        s4.getCompanies().add(co6);
        s5.getCompanies().add(co7);
        testService.saveAllCompanies(Arrays.asList(co1, co2, co3, co4, co5, co6, co7));

        ut1.getCompanySet().add(co1);
        ut1.getCompanySet().add(co2);
        ut1.getCompanySet().add(co3);
        ut1.getCompanySet().add(co4);
        ut1.getCompanySet().add(co5);
        ut2.getCompanySet().add(co6);
        ut3.getCompanySet().add(co7);

        Stock st1 = new Stock(6.5, 8.67, "BPAN4", StockType.PREFERRED, co1);
        Stock st2 = new Stock(8.5, 28.90, "ITUB3", StockType.ORDINARY, co2);
        Stock st3 = new Stock(8.8, 33.62, "ITUB4", StockType.PREFERRED, co2);
        Stock st4 = new Stock(7.5, 10.73, "CMIG4", StockType.PREFERRED, co3);
        Stock st5 = new Stock(7.1, 18.08, "NEOE3", StockType.ORDINARY, co4);
        Stock st6 = new Stock(6.9, 25.70, "AGRO3", StockType.ORDINARY, co5);
        Stock st7 = new Stock(7.2, 21.42, "CURY3", StockType.ORDINARY, co7);
        Stock st8 = new Stock(7.3, 22.42, "CURY4", StockType.ORDINARY, co7);

        st1.setCompany(co1);
        st2.setCompany(co2);
        st3.setCompany(co2);
        st4.setCompany(co3);
        st5.setCompany(co4);
        st6.setCompany(co5);
        st7.setCompany(co7);
        st8.setCompany(co7);
        co1.getStocks().add(st1);
        co2.getStocks().add(st2);
        co2.getStocks().add(st3);
        co3.getStocks().add(st4);
        co4.getStocks().add(st5);
        co5.getStocks().add(st6);
        co7.getStocks().add(st7);
        co7.getStocks().add(st8);
        testService.saveAllStocks(Arrays.asList(st1, st2, st3, st4, st5, st6, st7, st8));

        ut1.getStockSet().add(st1);
        ut1.getStockSet().add(st2);
        ut1.getStockSet().add(st3);
        ut1.getStockSet().add(st4);
        ut1.getStockSet().add(st5);
        ut1.getStockSet().add(st6);
        ut3.getStockSet().add(st7);
        ut3.getStockSet().add(st8);
    }

    public static Map<String, UserTest> getUserTestMap() {
        return userTestMap;
    }
}