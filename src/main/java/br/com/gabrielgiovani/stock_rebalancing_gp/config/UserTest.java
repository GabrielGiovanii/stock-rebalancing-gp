package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.*;

import java.util.HashSet;
import java.util.Set;

public class UserTest {

    private final Integer id;
    private final String username;
    private final String password;
    private final String fullName;

    private final Set<Portfolio> portfolioSet;

    private final Set<Category> categorySet;

    private final Set<Sector> sectorSet;

    private final Set<CategorySector> categorySectorSet;

    private final Set<Company> companySet;

    private final Set<Stock> stockSet;

    public UserTest(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.portfolioSet = new HashSet<>();
        this.categorySet = new HashSet<>();
        this.sectorSet = new HashSet<>();
        this.categorySectorSet = new HashSet<>();
        this.companySet = new HashSet<>();
        this.stockSet = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Portfolio> getPortfolioSet() {
        return portfolioSet;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public Set<Sector> getSectorSet() {
        return sectorSet;
    }

    public Set<CategorySector> getCategorySectorSet() {
        return categorySectorSet;
    }

    public Set<Company> getCompanySet() {
        return companySet;
    }

    public Set<Stock> getStockSet() {
        return stockSet;
    }
}