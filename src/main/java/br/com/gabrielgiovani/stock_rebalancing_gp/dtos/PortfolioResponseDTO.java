package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;

import java.util.Objects;

public class PortfolioResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private Double investmentPercentage;

    public PortfolioResponseDTO() {
    }

    public PortfolioResponseDTO(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.name = portfolio.getName();
        this.description = portfolio.getDescription();
        this.investmentPercentage = portfolio.getInvestmentPercentage();
    }

    public PortfolioResponseDTO(PortfolioSaveDTO portfolioSaveDTO) {
        this.id = portfolioSaveDTO.getId();
        this.name = portfolioSaveDTO.getName();
        this.description = portfolioSaveDTO.getDescription();
        this.investmentPercentage = portfolioSaveDTO.getInvestmentPercentage();
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PortfolioResponseDTO that = (PortfolioResponseDTO) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(investmentPercentage, that.investmentPercentage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(investmentPercentage);
        return result;
    }
}