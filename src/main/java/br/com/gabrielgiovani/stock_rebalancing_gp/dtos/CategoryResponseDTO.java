package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;

import java.util.Objects;

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

    public CategoryResponseDTO(CategorySaveDTO categorySaveDTO) {
        this.id = categorySaveDTO.getId();
        this.name = categorySaveDTO.getName();
        this.description = categorySaveDTO.getDescription();
        this.percentageUnderPortfolio = categorySaveDTO.getPercentageUnderPortfolio();
        this.portfolioResponseDTO = new PortfolioResponseDTO();
        this.portfolioResponseDTO.setId(categorySaveDTO.getPortfolioId());
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CategoryResponseDTO that = (CategoryResponseDTO) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(percentageUnderPortfolio, that.percentageUnderPortfolio);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(percentageUnderPortfolio);
        return result;
    }
}