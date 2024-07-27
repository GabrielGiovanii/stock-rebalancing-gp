package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;

public class CategoryResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private Double  percentageUnderPortfolio;

    private PortfolioResponseDTO portfolioResponseDTO;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.percentageUnderPortfolio = category.getPercentageUnderPortfolio();
        this.portfolioResponseDTO = new PortfolioResponseDTO(category.getPortfolio());
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

    public PortfolioResponseDTO getPortfolioResponseDTO() {
        return portfolioResponseDTO;
    }

    public void setPortfolioResponseDTO(PortfolioResponseDTO portfolioResponseDTO) {
        this.portfolioResponseDTO = portfolioResponseDTO;
    }
}