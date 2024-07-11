package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "category")
public class Category {

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
    private Double  percentageUnderPortfolio;

    public Category() {
    }

    public Category(String name, String description, Double percentageUnderPortfolio) {
        this.name = name;
        this.description = description;
        this.percentageUnderPortfolio = percentageUnderPortfolio;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPercentageUnderPortfolio() {
        return percentageUnderPortfolio;
    }
}