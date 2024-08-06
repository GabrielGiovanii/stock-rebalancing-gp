package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cnpj", "sector_id"})
})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String corporateName;

    @Column(length = 200)
    private String tradeName;

    @Column(nullable = false, columnDefinition = "CHAR(14)")
    private String cnpj;

    @Column(nullable = false, length = 200)
    private String bookkeeperBank;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @OneToMany(mappedBy = "company")
    private final Set<Stock> stocks;

    public Company() {
        this.stocks = new HashSet<>();
    }

    public Company(String corporateName, String tradeName, String cnpj, String bookkeeperBank, Sector sector) {
        this.corporateName = corporateName;
        this.tradeName = tradeName;
        this.cnpj = cnpj;
        this.bookkeeperBank = bookkeeperBank;
        this.sector = sector;
        this.stocks = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getBookkeeperBank() {
        return bookkeeperBank;
    }

    public void setBookkeeperBank(String bookkeeperBank) {
        this.bookkeeperBank = bookkeeperBank;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }
}