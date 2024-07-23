package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "portfolio")
    private List<Category> categories;

    public Portfolio() {
        this.categories = new ArrayList<>();
    }

    public Portfolio(String name, String description, Double investmentPercentage) {
        this.categories = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.investmentPercentage = investmentPercentage;
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

    public List<Category> getCategories() {
        return categories;
    }
}