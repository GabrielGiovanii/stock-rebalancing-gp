package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import br.com.gabrielgiovani.stock_rebalancing_gp.enums.StockType;
import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double technicalNote;

    @Column(nullable = false)
    private Double quotation;

    @Column(length = 10, nullable = false)
    private String tradingCode;

    @Column(nullable = false)
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Stock() {
    }

    public Stock(Double technicalNote, Double quotation, String tradingCode, StockType type, Company company) {
        this.technicalNote = technicalNote;
        this.quotation = quotation;
        this.tradingCode = tradingCode;
        this.type = type.getCode();
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTechnicalNote() {
        return technicalNote;
    }

    public void setTechnicalNote(Double technicalNote) {
        this.technicalNote = technicalNote;
    }

    public Double getQuotation() {
        return quotation;
    }

    public void setQuotation(Double quotation) {
        this.quotation = quotation;
    }

    public String getTradingCode() {
        return tradingCode;
    }

    public void setTradingCode(String tradingCode) {
        this.tradingCode = tradingCode;
    }

    public StockType getType() {
        return StockType.valueOf(type);
    }

    public void setType(StockType type) {
        this.type = type.getCode();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}