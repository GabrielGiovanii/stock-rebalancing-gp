package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.pk.CategorySectorPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "category_sector",
        uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "sector_id"}))
public class CategorySector {

    @EmbeddedId
    private CategorySectorPK id;

    private Double percentUnderCategory;

    public CategorySector() {
        this.id = new CategorySectorPK();
    }

    public CategorySector(Category category, Sector sector, Double percentUnderCategory) {
        this.id = new CategorySectorPK();
        this.id.setCategory(category);
        this.id.setSector(sector);

        this.percentUnderCategory = percentUnderCategory;
    }

    public CategorySectorPK getId() {
        return id;
    }

    public void setId(CategorySectorPK id) {
        this.id = id;
    }

    public Double getPercentUnderCategory() {
        return percentUnderCategory;
    }

    public void setPercentUnderCategory(Double percentUnderCategory) {
        this.percentUnderCategory = percentUnderCategory;
    }
}