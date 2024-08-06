package br.com.gabrielgiovani.stock_rebalancing_gp.config;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.*;

import java.util.HashSet;
import java.util.Set;

public class UserTest {

    private final Integer id;
    private final String username;
    private final String password;
    private final String fullName;

    private final Set<Portfolio> portfolioMap;

    private final Set<Category> categoryMap;

    private final Set<Sector> sectorMap;

    private final Set<CategorySector> categorySectorMap;

    private final Set<Company> companyMap;

    private final Set<Stock> stockSet;

    public UserTest(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.portfolioMap = new HashSet<>();
        this.categoryMap = new HashSet<>();
        this.sectorMap = new HashSet<>();
        this.categorySectorMap = new HashSet<>();
        this.companyMap = new HashSet<>();
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

    public Set<Portfolio> getPortfolioMap() {
        return portfolioMap;
    }

    public Set<Category> getCategoryMap() {
        return categoryMap;
    }

    public Set<Sector> getSectorMap() {
        return sectorMap;
    }

    public Set<CategorySector> getCategorySectorMap() {
        return categorySectorMap;
    }

    public Set<Company> getCompanyMap() {
        return companyMap;
    }

    public Set<Stock> getStockSet() {
        return stockSet;
    }
}