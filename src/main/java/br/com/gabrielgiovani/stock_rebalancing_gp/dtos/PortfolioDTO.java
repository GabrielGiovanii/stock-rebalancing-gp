package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class PortfolioDTO {

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

    public PortfolioDTO() {
    }

    public PortfolioDTO(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.name = portfolio.getName();
        this.description = portfolio.getDescription();
        this.investmentPercentage = portfolio.getInvestmentPercentage();
        this.userId = portfolio.getUser().getId();
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PortfolioDTO that = (PortfolioDTO) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(investmentPercentage, that.investmentPercentage) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(investmentPercentage);
        result = 31 * result + Objects.hashCode(userId);
        return result;
    }
}