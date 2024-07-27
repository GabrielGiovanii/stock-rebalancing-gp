package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class PortfolioSaveDTO {

    private Integer id;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 50, message = "{size.must.be.between}")
    private String name;

    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String description;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    @DecimalMin(value = "1.00", message = "{validation.decimalmin}")
    @DecimalMax(value =  "100.00", message = "{validation.decimalmax}")
    private Double investmentPercentage;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private Integer userId;

    public PortfolioSaveDTO() {
    }

    public PortfolioSaveDTO(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.name = portfolio.getName();
        this.description = portfolio.getDescription();
        this.investmentPercentage = portfolio.getInvestmentPercentage();
        this.userId = Objects.nonNull(portfolio.getUser()) ? portfolio.getUser().getId() : null;
    }

    public Integer getId() {
        return id;
    }

    public PortfolioSaveDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PortfolioSaveDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PortfolioSaveDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getInvestmentPercentage() {
        return investmentPercentage;
    }

    public void setInvestmentPercentage(Double investmentPercentage) {
        this.investmentPercentage = investmentPercentage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}