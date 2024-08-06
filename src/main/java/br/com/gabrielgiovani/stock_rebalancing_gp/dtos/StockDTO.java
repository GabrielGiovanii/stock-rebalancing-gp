package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Stock;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class StockDTO {

    private Integer id;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    @DecimalMin(value = "1.00", message = "{validation.decimalmin}")
    @DecimalMax(value =  "10.00", message = "{validation.decimalmax}")
    private Double technicalNote;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    @DecimalMin(value = "0.01", message = "{validation.decimalmin}")
    private Double quotation;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 5, max = 10, message = "{size.must.be.between}")
    private String tradingCode;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private Integer stockTypeCode;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private Integer companyId;

    public StockDTO() {
    }

    public StockDTO(Stock stock) {
        this.id = stock.getId();
        this.technicalNote = stock.getTechnicalNote();
        this.quotation = stock.getQuotation();
        this.tradingCode = stock.getTradingCode();
        this.stockTypeCode = stock.getType().getCode();
        this.companyId = stock.getCompany().getId();
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

    public Integer getStockTypeCode() {
        return stockTypeCode;
    }

    public void setStockTypeCode(Integer stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        StockDTO stockDTO = (StockDTO) object;
        return Objects.equals(id, stockDTO.id) && Objects.equals(technicalNote, stockDTO.technicalNote) && Objects.equals(quotation, stockDTO.quotation) && Objects.equals(tradingCode, stockDTO.tradingCode) && Objects.equals(stockTypeCode, stockDTO.stockTypeCode) && Objects.equals(companyId, stockDTO.companyId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(technicalNote);
        result = 31 * result + Objects.hashCode(quotation);
        result = 31 * result + Objects.hashCode(tradingCode);
        result = 31 * result + Objects.hashCode(stockTypeCode);
        result = 31 * result + Objects.hashCode(companyId);
        return result;
    }
}