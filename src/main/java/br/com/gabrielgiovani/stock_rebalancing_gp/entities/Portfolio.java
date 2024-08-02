package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(name = "percentage_under_portfolio", nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double investmentPercentage;

    @OneToOne
    @JoinColumn(unique = true, name = "user_id")
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private final Set<Category> categories;

    public Portfolio() {
        this.categories = new HashSet<>();
    }

    public Portfolio(String name, String description, Double investmentPercentage, User user) {
        this.name = name;
        this.description = description;
        this.investmentPercentage = investmentPercentage;
        this.categories = new HashSet<>();
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInvestmentPercentage() {
        return investmentPercentage;
    }

    public void setInvestmentPercentage(Double investmentPercentage) {
        this.investmentPercentage = investmentPercentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }
}