package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "portfolio_id"})
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double percentageUnderPortfolio;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "id.category")
    private final Set<CategorySector> categorySectors;

    public Category() {
        this.categorySectors = new HashSet<>();
    }

    public Category(String name, String description, Double percentageUnderPortfolio, Portfolio portfolio) {
        this.name = name;
        this.description = description;
        this.percentageUnderPortfolio = percentageUnderPortfolio;
        this.portfolio = portfolio;
        this.categorySectors = new HashSet<>();
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

    public Set<CategorySector> getCategorySectors() {
        return categorySectors;
    }
}