package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(nullable = true, length = 200)
    private String description;

    @Column(name = "percentage_under_portfolio", nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double percentageUnderPortfolio;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    @Column(nullable = false)
    private Portfolio portfolio;

    public Category() {
    }

    public Category(String name, String description, Double percentageUnderPortfolio, Portfolio portfolio) {
        this.name = name;
        this.description = description;
        this.percentageUnderPortfolio = percentageUnderPortfolio;
        this.portfolio = portfolio;
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

    public Double getPercentageUnderPortfolio() {
        return percentageUnderPortfolio;
    }

    public void setPercentageUnderPortfolio(Double percentageUnderPortfolio) {
        this.percentageUnderPortfolio = percentageUnderPortfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}