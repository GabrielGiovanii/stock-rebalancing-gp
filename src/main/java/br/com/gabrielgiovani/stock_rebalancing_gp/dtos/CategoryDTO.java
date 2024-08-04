package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class CategoryDTO {

    private Integer id;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 50, message = "{size.must.be.between}")
    private String name;

    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String description;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    @DecimalMin(value = "1.00", message = "{validation.decimalmin}")
    @DecimalMax(value =  "100.00", message = "{validation.decimalmax}")
    private Double  percentageUnderPortfolio;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private Integer portfolioId;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.percentageUnderPortfolio = category.getPercentageUnderPortfolio();
        this.portfolioId = category.getPortfolio().getId();
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

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CategoryDTO that = (CategoryDTO) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(percentageUnderPortfolio, that.percentageUnderPortfolio) && Objects.equals(portfolioId, that.portfolioId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(percentageUnderPortfolio);
        result = 31 * result + Objects.hashCode(portfolioId);
        return result;
    }
}