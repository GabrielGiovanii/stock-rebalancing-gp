package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import br.com.gabrielgiovani.stock_rebalancing_gp.enums.SectorNature;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sector")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private Integer sectorNature;

    @OneToMany(mappedBy = "id.sector", fetch = FetchType.EAGER)
    private final Set<CategorySector> categoriesSector;

    @OneToMany(mappedBy = "sector")
    private final Set<Company> companies;

    public Sector() {
        this.categoriesSector = new HashSet<>();
        this.companies = new HashSet<>();
    }

    public Sector(String name, String description, SectorNature sectorNature) {
        this.name = name;
        this.description = description;
        this.sectorNature = sectorNature.getCode();
        this.categoriesSector = new HashSet<>();
        this.companies = new HashSet<>();
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

    public SectorNature getSectorNature() {
        return SectorNature.valueOf(sectorNature);
    }

    public void setSectorNature(SectorNature sectorNature) {
        this.sectorNature = sectorNature.getCode();
    }

    public Set<CategorySector> getCategoriesSector() {
        return categoriesSector;
    }

    public Set<Company> getCompanies() {
        return companies;
    }
}