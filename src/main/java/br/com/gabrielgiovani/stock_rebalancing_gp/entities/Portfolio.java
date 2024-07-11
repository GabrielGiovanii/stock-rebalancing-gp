package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 50, message = "{size.must.be.between}")
    @Column(unique = true)
    private String name;

    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String description;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    @DecimalMin(value = "1.00", message = "{validation.decimalmin}")
    @DecimalMax(value =  "100.00", message = "{validation.decimalmax}")
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
