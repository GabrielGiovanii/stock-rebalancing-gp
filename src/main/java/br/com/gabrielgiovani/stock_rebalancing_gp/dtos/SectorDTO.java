package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Sector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SectorDTO {

    private Integer id;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 100, message = "{size.must.be.between}")
    private String name;

    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String description;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private Integer sectorNatureCode;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private final Set<Integer> categoryIds;

    public SectorDTO() {
        this.categoryIds = new HashSet<>();
    }

    public SectorDTO(Sector sector) {
        this.id = sector.getId();
        this.name = sector.getName();
        this.description = sector.getDescription();
        this.sectorNatureCode = sector.getSectorNature().getCode();
        this.categoryIds = sector.getCategoriesSector()
                .stream()
                .map(obj -> obj.getId().getCategory().getId())
                .collect(Collectors.toSet());
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

    public Integer getSectorNatureCode() {
        return sectorNatureCode;
    }

    public void setSectorNatureCode(Integer sectorNatureCode) {
        this.sectorNatureCode = sectorNatureCode;
    }

    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        SectorDTO sectorDTO = (SectorDTO) object;
        return Objects.equals(id, sectorDTO.id) && Objects.equals(name, sectorDTO.name) && Objects.equals(description, sectorDTO.description) && Objects.equals(sectorNatureCode, sectorDTO.sectorNatureCode) && categoryIds.equals(sectorDTO.categoryIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(sectorNatureCode);
        result = 31 * result + categoryIds.hashCode();
        return result;
    }
}