package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double investmentPercentage;

    public Portfolio() {
    }

    public Portfolio(String name, String description, Double investmentPercentage) {
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
}